package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.AccessConvert;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.ConfigField;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.kafka.producer.CommonProducer;
import cn.wnhyang.coolGuard.mapper.AccessMapper;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.AccessService;
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
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.ACCESS_NAME_EXIST;
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

    @Override
    public Map<String, Object> syncRisk(String name, Map<String, String> params) {
        log.info("服务名：{}, 入参：{}", name, params);

        Map<String, Object> result = new HashMap<>();

        // 根据接入名称获取接入
        Access access = getAccessByName(name);
        log.info("access: {}", access);

        // 设置接入
        List<InputFieldVO> inputFieldList = getAccessInputFieldList(access);
        List<OutputFieldVO> outputFieldList = getAccessOutputFieldList(access);

        FieldContext fieldContext = fieldService.fieldParse(inputFieldList, params);
        PolicyContext policyContext = new PolicyContext();
        EventContext eventContext = new EventContext();
        IndicatorContext indicatorContext = new IndicatorContext();

        LiteflowResponse syncRisk = flowExecutor.execute2Resp(StrUtil.format(LFUtil.ACCESS_CHAIN, access.getName()), null, fieldContext, indicatorContext, policyContext, eventContext);
        if (!syncRisk.isSuccess()) {
            throw exception(Integer.valueOf(syncRisk.getCode()), syncRisk.getMessage());
        }
        // TODO chain el 打印

        result.put("policySetResult", eventContext.getPolicySetResult());
        // 设置出参
        Map<String, Object> outFields = new HashMap<>();
        outFields.put(FieldName.seqId, fieldContext.getStringData(FieldName.seqId));
        // TODO 增加接口耗时和流程耗时
        if (CollUtil.isNotEmpty(outputFieldList)) {
            for (OutputFieldVO outputField : outputFieldList) {
                outFields.put(outputField.getParamName(), fieldContext.getStringData(outputField.getName()));
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
    public Map<String, Object> asyncRisk(String name, Map<String, String> params) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", "000000");

        // TODO 异步决策不需要跑策略
        try {
            return asyncExecutor.submit(() ->
                    syncRisk(name, params)).get(100, TimeUnit.MILLISECONDS);
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
        if (accessMapper.selectByName(createVO.getName()) != null) {
            throw exception(ACCESS_NAME_EXIST);
        }
        Access access = AccessConvert.INSTANCE.convert(createVO);
        accessMapper.insert(access);
        // TODO 创建chain
        String aChain = StrUtil.format(LFUtil.ACCESS_CHAIN, access.getName());
        chainMapper.insert(new Chain().setChainName(aChain));
        return access.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccess(AccessUpdateVO updateVO) {
        // TODO 是否可删除，最近有数据？
        Access access = AccessConvert.INSTANCE.convert(updateVO);

        accessMapper.updateById(access);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        accessMapper.deleteById(id);
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.ACCESS_CHAIN, access.getName()));
    }

    @Override
    public AccessVO getAccess(Long id) {
        Access access = accessMapper.selectById(id);
        return AccessConvert.INSTANCE.convert(access);
    }

    @Override
    public PageResult<AccessVO> pageAccess(AccessPageVO pageVO) {
        PageResult<Access> pageResult = accessMapper.selectPage(pageVO);
        return AccessConvert.INSTANCE.convert(pageResult);
    }

    @Override
    public Access getAccessByName(String name) {
        Access access = accessMapper.selectByName(name);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        return access;
    }

    @Override
    public List<InputFieldVO> getAccessInputFieldList(Access access) {
        List<ConfigField> configFieldList = access.getInputConfig();

        List<InputFieldVO> inputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(configFieldList)) {
            for (ConfigField configField : configFieldList) {
                Field field = fieldMapper.selectByName(configField.getFieldName());
                if (field != null) {
                    InputFieldVO inputFieldVO = FieldConvert.INSTANCE.convert2InputFieldVO(field);
                    inputFieldVO.setParamName(configField.getParamName());
                    inputFieldVO.setRequired(configField.getRequired());
                    inputFieldVOList.add(inputFieldVO);
                }
            }
        }
        return inputFieldVOList;
    }

    @Override
    public List<OutputFieldVO> getAccessOutputFieldList(Access access) {
        List<ConfigField> configFieldList = access.getOutputConfig();

        List<OutputFieldVO> outputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(configFieldList)) {
            for (ConfigField configField : configFieldList) {
                Field field = fieldMapper.selectByName(configField.getFieldName());
                if (field != null) {
                    outputFieldVOList.add(new OutputFieldVO()
                            .setName(configField.getFieldName())
                            .setParamName(configField.getParamName()));
                }
            }
        }
        return outputFieldVOList;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.EMPTY_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "空组件")
    public void empty(NodeComponent bindCmp) {
        log.info("空组件");
    }

}
