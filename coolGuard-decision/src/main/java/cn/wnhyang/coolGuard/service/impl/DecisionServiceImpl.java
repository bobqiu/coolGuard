package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.constant.RouteStatus;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.StrategyContext;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.mapper.DisposalMapper;
import cn.wnhyang.coolGuard.service.DecisionService;
import cn.wnhyang.coolGuard.service.ServiceConfigFieldService;
import cn.wnhyang.coolGuard.service.ServiceConfigService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
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

    private final ServiceConfigService serviceConfigService;

    private final ServiceConfigFieldService serviceConfigFieldService;

    private final DisposalMapper disposalMapper;

    private final FlowExecutor flowExecutor;

    private static final List<Indicator> INDICATOR_LIST = List.of(
            new Indicator().setId(1L).setName("最近24小时客户号向某一账号转账次数").setStatus(true).setType("count").setCalcField("N_F_money").setWinSize("H").setWinType("last").setWinCount(24)
                    .setTimeSlice(60 * 60L).setMasterField("N_S_customerId").setSlaveFields("N_S_recCardNo").setComputeScript("1213124").setVersion(1),
            new Indicator().setId(2L).setName("最近2分钟客户号向某一账号转账金额之和").setStatus(true).setType("sum").setCalcField("N_F_money").setWinSize("m").setWinType("last").setWinCount(2)
                    .setTimeSlice(60L).setMasterField("N_S_customerId").setSlaveFields("N_S_recCardNo").setComputeScript("1213124").setVersion(1));

    @Override
    public DecisionResponse syncRisk(String name, Map<String, String> params) {

        DecisionResponse decisionResponse = new DecisionResponse();

        // 根据服务配置名称获取服务配置
        ServiceConfig serviceConfig = serviceConfigService.getServiceConfigByName(name);

        // 设置服务配置
        List<InputFieldVO> inputFields = serviceConfigFieldService.getServiceConfigInputFieldsByServiceId(serviceConfig.getId());

        DecisionRequest decisionRequest = new DecisionRequest(RouteStatus.STRATEGY_SET_P, name, params, serviceConfig, inputFields);
        StrategyContext strategyContext = new StrategyContext();
        for (Disposal disposal : disposalMapper.selectList()) {
            strategyContext.addDisposal(disposal.getId(), disposal);
        }
        IndicatorContext indicatorContext = new IndicatorContext();

        LiteflowResponse syncRisk = flowExecutor.execute2Resp("decisionChain", null, decisionRequest, indicatorContext, strategyContext, decisionResponse);


        return decisionResponse;
    }

    @Override
    public DecisionResponse asyncRisk(String name, Map<String, String> params) {
        log.info("name {}", name);

        DecisionResponse decisionResponse = new DecisionResponse();
        return decisionResponse;
    }


}
