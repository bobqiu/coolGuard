package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.wnhyang.coolGuard.constant.AccessMode;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.ParamConfig;
import cn.wnhyang.coolGuard.kafka.producer.CommonProducer;
import cn.wnhyang.coolGuard.mapper.AccessMapper;
import cn.wnhyang.coolGuard.service.*;
import cn.wnhyang.coolGuard.util.JsonUtil;
import cn.wnhyang.coolGuard.vo.AccessVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wnhyang
 * @date 2025/3/8
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {

    private final AsyncTaskExecutor asyncExecutor;

    private final CommonProducer commonProducer;

    private final AccessMapper accessMapper;

    private final AccessService accessService;

    private final FieldService fieldService;

    private final IndicatorService indicatorService;

    private final PolicySetService policySetService;

    private Map<String, Object> access(String code, Map<String, String> params, String mode) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("接入");
        log.info("服务名：{}, 入参：{}", code, params);

        Map<String, Object> result = new HashMap<>(16);
        try {
            stopWatch.stop();

            stopWatch.start("accessDB");
            // 根据接入名称获取接入
            AccessVO accessVO = accessService.getAccessByCode(code);
            stopWatch.stop();

            stopWatch.start("字段解析");
            // 事件开始
            DecisionContextHolder.setEventContext(new EventContext());

            // 字段解析
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
            Map<String, Object> outFields = new HashMap<>(16);
            outFields.put(FieldCode.SEQ_ID, fieldContext.getData(FieldCode.SEQ_ID, String.class));
            if (CollUtil.isNotEmpty(accessVO.getOutputFieldList())) {
                for (ParamConfig outputField : accessVO.getOutputFieldList()) {
                    outFields.put(outputField.getParamName(), fieldContext.getData2String(outputField.getFieldCode()));
                }
            }
            result.put("outFields", outFields);
            stopWatch.stop();

            // 将上下文拼在一块，将此任务丢到线程中执行
            stopWatch.start("ES");
            Map<String, Object> esData = new HashMap<>(16);
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

        Map<String, Object> map = new HashMap<>(16);
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
}
