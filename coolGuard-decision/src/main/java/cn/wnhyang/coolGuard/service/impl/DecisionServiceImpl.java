package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.mapper.DisposalMapper;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.service.DecisionService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final DisposalMapper disposalMapper;

    private final FlowExecutor flowExecutor;

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
        for (Disposal disposal : disposalMapper.selectList()) {
            policyContext.addDisposal(disposal.getId(), disposal);
        }
        IndicatorContext indicatorContext = new IndicatorContext();

        LiteflowResponse syncRisk = flowExecutor.execute2Resp("decisionChain", null, decisionRequest, indicatorContext, policyContext, decisionResponse);


        return decisionResponse;
    }

    @Override
    public DecisionResponse asyncRisk(String name, Map<String, String> params) {
        log.info("name {}", name);

        DecisionResponse decisionResponse = new DecisionResponse();
        return decisionResponse;
    }


}
