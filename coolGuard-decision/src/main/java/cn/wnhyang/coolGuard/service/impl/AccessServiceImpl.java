package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.constant.FieldRefType;
import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.AccessConvert;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.kafka.producer.CommonProducer;
import cn.wnhyang.coolGuard.mapper.AccessMapper;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.mapper.FieldRefMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.service.FieldRefService;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.util.JsonUtil;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.AccessVO;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.vo.update.AccessUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.ACCESS_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.ACCESS_NOT_EXIST;
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

    private final FlowExecutor flowExecutor;

    private final CommonProducer commonProducer;

    private final FieldMapper fieldMapper;

    private final ChainMapper chainMapper;

    private final FieldService fieldService;

    private final FieldRefService fieldRefService;

    private final FieldRefMapper fieldRefMapper;

    @Override
    public Map<String, Object> test(String code, Map<String, String> params) {
        Map<String, Object> result = syncRisk(code, params);
        accessMapper.updateByCode(new Access().setCode(code).setTestParams(params));
        return result;
    }

    @Override
    public Map<String, Object> syncRisk(String code, Map<String, String> params) {
        log.info("服务名：{}, 入参：{}", code, params);

        Map<String, Object> result = new HashMap<>();

        // 根据接入名称获取接入
        Access access = getAccessByCode(code);
        log.info("access: {}", access);

        // 设置接入
        List<InputFieldVO> inputFieldList = fieldRefService.getAccessInputFieldList(access);
        List<OutputFieldVO> outputFieldList = fieldRefService.getAccessOutputFieldList(access);

        FieldContext fieldContext = fieldService.fieldParse(inputFieldList, params);
        PolicyContext policyContext = new PolicyContext();
        EventContext eventContext = new EventContext();
        IndicatorContext indicatorContext = new IndicatorContext();

        LiteflowResponse syncRisk = flowExecutor.execute2Resp(StrUtil.format(LFUtil.ACCESS_CHAIN, access.getCode()), null, fieldContext, indicatorContext, policyContext, eventContext);
        if (!syncRisk.isSuccess()) {
            throw exception(Integer.valueOf(syncRisk.getCode()), syncRisk.getMessage());
        }
        // TODO chain el 打印

        result.put("policySetResult", eventContext.getPolicySetResult());
        // 设置出参
        Map<String, Object> outFields = new HashMap<>();
        outFields.put(FieldCode.seqId, fieldContext.getStringData(FieldCode.seqId));
        // TODO 增加接口耗时和流程耗时
        if (CollUtil.isNotEmpty(outputFieldList)) {
            for (OutputFieldVO outputField : outputFieldList) {
                outFields.put(outputField.getParamName(), fieldContext.getStringData(outputField.getCode()));
            }
        }
        result.put("outFields", outFields);

        // 将上下文拼在一块，将此任务丢到线程中执行
        Map<String, Object> esData = new HashMap<>();
        esData.put("fields", fieldContext);
        esData.put("zbs", indicatorContext.convert());
        esData.put("result", eventContext.getPolicySetResult());
        try {
            commonProducer.send(KafkaConstant.EVENT_ES_DATA, JsonUtil.toJsonString(esData));
        } catch (Exception e) {
            log.error("esData error", e);
        }
        return result;
    }

    @Override
    public Map<String, Object> asyncRisk(String code, Map<String, String> params) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", "000000");

        // TODO 异步决策不需要跑策略
        try {
            return asyncExecutor.submit(() ->
                    syncRisk(code, params)).get(100, TimeUnit.MILLISECONDS);
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
        // TODO 创建chain
        String aChain = StrUtil.format(LFUtil.ACCESS_CHAIN, access.getCode());
        chainMapper.insert(new Chain().setChainName(aChain).setElData(LFUtil.DEFAULT_ACCESS_CHAIN));
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
        // TODO 删除fieldRef
        fieldRefMapper.delete(FieldRefType.ACCESS, access.getCode(), null);
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.ACCESS_CHAIN, access.getCode()));
    }

    @Override
    public AccessVO getAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        AccessVO convert = AccessConvert.INSTANCE.convert(access);
        convert.setInputFieldList(fieldRefService.getAccessInputFieldList(access));
        convert.setOutputFieldList(fieldRefService.getAccessOutputFieldList(access));
        return convert;
    }

    @Override
    public PageResult<AccessVO> pageAccess(AccessPageVO pageVO) {
        PageResult<Access> pageResult = accessMapper.selectPage(pageVO);
        return AccessConvert.INSTANCE.convert(pageResult);
    }

    @Override
    public Access getAccessByCode(String code) {
        Access access = accessMapper.selectByCode(code);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        return access;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        String accessCode = access.getCode();
        access.setCode(access.getCode() + "_copy").setName(access.getName() + "_副本");
        Long insertId = createAccess(AccessConvert.INSTANCE.convert2Create(access));

        fieldRefService.copyAccess(accessCode, access.getCode());

        return insertId;
    }

}
