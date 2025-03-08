package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.wnhyang.coolGuard.constant.AccessMode;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.convert.AccessConvert;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.entity.ParamConfig;
import cn.wnhyang.coolGuard.kafka.producer.CommonProducer;
import cn.wnhyang.coolGuard.mapper.AccessMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.service.PolicySetService;
import cn.wnhyang.coolGuard.util.JsonUtil;
import cn.wnhyang.coolGuard.vo.AccessVO;
import cn.wnhyang.coolGuard.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.vo.update.AccessUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.ACCESS_CODE_EXIST;
import static cn.wnhyang.coolGuard.error.DecisionErrorCode.ACCESS_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 接入表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final AsyncTaskExecutor asyncExecutor;

    private final AccessMapper accessMapper;

    private final CommonProducer commonProducer;

    private final FieldService fieldService;

    private final IndicatorService indicatorService;

    private final PolicySetService policySetService;

    private Map<String, Object> access(String code, Map<String, String> params, String mode) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("接入");
        log.info("服务名：{}, 入参：{}", code, params);

        Map<String, Object> result = new HashMap<>();
        try {

            // 根据接入名称获取接入
            AccessVO accessVO = getAccessByCode(code);
            log.info("accessVO: {}", accessVO);
            stopWatch.stop();

            // 事件开始
            DecisionContextHolder.setEventContext(new EventContext());

            // 字段解析
            stopWatch.start("字段解析");
            FieldContext fieldContext = fieldService.fieldParse(accessVO.getInputFieldList(), params);
            DecisionContextHolder.setFieldContext(fieldContext);
            stopWatch.stop();
            // 指标计算
            stopWatch.start("指标计算");
            indicatorService.indicatorCompute();
            stopWatch.stop();

            if (!AccessMode.ASYNC.equals(mode)) {
                stopWatch.start("策略集");
                // 执行策略集
                policySetService.policySet();
                stopWatch.stop();
            }
            stopWatch.start("结果");
            // 策略结果
            result.put("policySetResult", DecisionContextHolder.getEventContext().getPolicySetResult());
            // 设置出参
            Map<String, Object> outFields = new HashMap<>();
            outFields.put(FieldCode.SEQ_ID, fieldContext.getData(FieldCode.SEQ_ID, String.class));
            // TODO 增加接口耗时和流程耗时
            if (CollUtil.isNotEmpty(accessVO.getOutputFieldList())) {
                for (ParamConfig outputField : accessVO.getOutputFieldList()) {
                    outFields.put(outputField.getParamName(), fieldContext.getData2String(outputField.getFieldCode()));
                }
            }
            result.put("outFields", outFields);
            stopWatch.stop();

            // 将上下文拼在一块，将此任务丢到线程中执行
            stopWatch.start("ES");
            Map<String, Object> esData = new HashMap<>();
            esData.put("fields", fieldContext);
            esData.put("zbs", DecisionContextHolder.getIndicatorContext().convert());
            esData.put("result", DecisionContextHolder.getEventContext().getPolicySetResult());
            try {
                commonProducer.send(KafkaConstant.EVENT_ES_DATA, JsonUtil.toJsonString(esData));
            } catch (Exception e) {
                log.error("esData error", e);
            }
            stopWatch.stop();
        } finally {
            DecisionContextHolder.removeAll();
        }
        log.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
        return result;
    }

    @Override
    public Map<String, Object> testRisk(String code, Map<String, String> params) {
        Map<String, Object> result = access(code, params, AccessMode.TEST);
        accessMapper.updateByCode(new Access().setCode(code).setTestParams(params));
        return result;
    }

    @Override
    public Map<String, Object> syncRisk(String code, Map<String, String> params) {
        return access(code, params, AccessMode.SYNC);
    }

    @Override
    public Map<String, Object> asyncRisk(String code, Map<String, String> params) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", "000000");

        try {
            return asyncExecutor.submit(() ->
                    access(code, params, AccessMode.ASYNC)).get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAccess(AccessCreateVO createVO) {
        // 1、校验服务name唯一性
        if (accessMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(ACCESS_CODE_EXIST);
        }
        Access access = AccessConvert.INSTANCE.convert(createVO);
        accessMapper.insert(access);
        return access.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccess(AccessUpdateVO updateVO) {
        Access access = accessMapper.selectById(updateVO.getId());
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        Access convert = AccessConvert.INSTANCE.convert(updateVO);
        accessMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        accessMapper.deleteById(id);
    }

    @Override
    public AccessVO getAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        AccessVO accessVO = AccessConvert.INSTANCE.convert(access);
        fillField(accessVO);
        return accessVO;
    }

    @Override
    public PageResult<AccessVO> pageAccess(AccessPageVO pageVO) {
        PageResult<Access> pageResult = accessMapper.selectPage(pageVO);
        return AccessConvert.INSTANCE.convert(pageResult);
    }

    @Override
    public AccessVO getAccessByCode(String code) {
        Access access = accessMapper.selectByCode(code);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        AccessVO accessVO = AccessConvert.INSTANCE.convert(access);
        fillField(accessVO);
        return accessVO;
    }

    private void fillField(AccessVO accessVO) {
        Map<String, Field> fields = fieldService.getFields();
        accessVO.getInputFieldList().forEach(inputField -> {
            fillField(inputField, fields);
        });
        accessVO.getOutputFieldList().forEach(outputField -> {
            fillField(outputField, fields);
        });
    }

    private void fillField(ParamConfig paramConfig, Map<String, Field> fields) {
        Field field = fields.get(paramConfig.getFieldCode());
        if (field != null) {
            paramConfig.setName(field.getName());
            paramConfig.setType(field.getType());
            paramConfig.setDynamic(field.getDynamic());
            paramConfig.setScript(field.getScript());
            paramConfig.setInfo(field.getInfo());
        }
    }

}
