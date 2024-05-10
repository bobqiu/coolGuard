package cn.wnhyang.coolGuard.context;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.vo.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
@Data
public class StrategyContext {

    private final Map<Long, Disposal> disposalMap = new ConcurrentHashMap<>();

    private StrategySetVO strategySetVO;

    private final Map<Long, StrategyVO> strategyMap = new ConcurrentHashMap<>();

    private final Map<Long, List<RuleVO>> ruleListMap = new ConcurrentHashMap<>();

    public void addDisposal(Long id, Disposal disposal) {
        disposalMap.put(id, disposal);
    }

    public Disposal getDisposal(Long id) {
        return disposalMap.get(id);
    }

    public boolean hasDisposal(Long id) {
        return disposalMap.containsKey(id);
    }

    public void addStrategy(Long id, StrategyVO strategyVO) {
        strategyMap.put(id, strategyVO);
    }

    public StrategyVO getStrategy(Long id) {
        return strategyMap.get(id);
    }

    public boolean hasStrategy(Long id) {
        return strategyMap.containsKey(id);
    }

    public void initRuleList(Long id) {
        ruleListMap.put(id, new ArrayList<>());
    }

    public void addRuleVO(Long id, RuleVO ruleVO) {
        ruleListMap.get(id).add(ruleVO);
    }

    public List<RuleVO> getRuleList(Long id) {
        return ruleListMap.get(id);
    }

    public boolean hasRuleList(Long id) {
        return ruleListMap.containsKey(id);
    }

    public StrategySetResult convert() {
        StrategySetResult strategySetResult = new StrategySetResult(strategySetVO.getName(), strategySetVO.getCode());
        // TODO
        strategySetResult.setDisposalName("通过");
        strategySetResult.setDisposalCode("pass");
        for (Map.Entry<Long, StrategyVO> entry : strategyMap.entrySet()) {
            StrategyVO strategyVO = entry.getValue();
            StrategyResult strategyResult = new StrategyResult(strategyVO.getName(), strategyVO.getCode(), strategyVO.getMode());
            // TODO
            strategyResult.setDisposalName("通过");
            strategyResult.setDisposalCode("pass");
            List<RuleVO> ruleVOList = ruleListMap.get(strategyVO.getId());
            if (CollUtil.isNotEmpty(ruleVOList)) {
                for (RuleVO ruleVO : ruleVOList) {
                    RuleResult ruleResult = new RuleResult(ruleVO.getName(), ruleVO.getCode());
                    // TODO 模拟规则不返回
                    Disposal disposal = disposalMap.get(ruleVO.getDisposalId());
                    if (null != disposal) {
                        ruleResult.setDisposalName(disposal.getName());
                        ruleResult.setDisposalCode(disposal.getCode());
                    }
                    ruleResult.setScore(ruleVO.getScore());
                    strategyResult.addRuleResult(ruleResult);
                }
            }
            strategySetResult.addStrategyResult(strategyResult);
        }

        return strategySetResult;
    }

}
