package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.kafka.producer.CommonProducer;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.service.DecisionService;
import cn.wnhyang.coolGuard.service.DisposalService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {

    private final AccessService accessService;

    private final DisposalService disposalService;

    private final FlowExecutor flowExecutor;

    private final CommonProducer commonProducer;

    private final ObjectMapper objectMapper;

    @Override
    public DecisionResponse syncRisk(String name, Map<String, String> params) {

        DecisionResponse decisionResponse = new DecisionResponse();

        // 根据接入名称获取接入
        Access access = accessService.getAccessByName(name);

        // 设置接入
        List<InputFieldVO> inputFields = accessService.getAccessInputFieldList(access.getId());
        List<OutputFieldVO> outputFields = accessService.getAccessOutputFieldList(access.getId());

        DecisionRequest decisionRequest = new DecisionRequest(name, params, access, inputFields, outputFields);
        PolicyContext policyContext = new PolicyContext();
        for (Disposal disposal : disposalService.listDisposal()) {
            policyContext.addDisposal(disposal.getId(), disposal);
        }
        IndicatorContext indicatorContext = new IndicatorContext();

        LiteflowResponse syncRisk = flowExecutor.execute2Resp("decisionChain", null, decisionRequest, indicatorContext, policyContext, decisionResponse);

        // 将上下文拼在一块，将此任务丢到线程中执行
        Map<String, Object> esData = new HashMap<>();
        esData.put("fields", decisionRequest.getFields());
        esData.put("zbs", indicatorContext.convert());
        esData.put("result", decisionResponse.getPolicySetResult());
        try {
            commonProducer.send(KafkaConstant.EVENT_ES_DATA, objectMapper.writeValueAsString(esData));
        } catch (JsonProcessingException e) {
            log.error("esData json error", e);
        } catch (Exception e) {
            log.error("esData error", e);
        }
        return decisionResponse;
    }

    @Override
    public DecisionResponse asyncRisk(String name, Map<String, String> params) {
        log.info("name {}", name);

        DecisionResponse decisionResponse = new DecisionResponse();
        return decisionResponse;
    }


}
