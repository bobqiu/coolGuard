package cn.wnhyang.coolGuard.context;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.vo.*;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
@Data
public class PolicyContext {

    private final Map<String, Disposal> disposalMap = new ConcurrentHashMap<>();

    private PolicySetVO policySetVO;

    private final Map<Long, PolicyVO> policyMap = new ConcurrentHashMap<>();

    private final Map<String, List<RuleVO>> ruleListMap = new ConcurrentHashMap<>();

    private final Map<String, List<RuleVO>> hitRuleListMap = new ConcurrentHashMap<>();

    public void addDisposal(String disposalCode, Disposal disposal) {
        disposalMap.put(disposalCode, disposal);
    }

    public void addPolicy(long id, PolicyVO policyVO) {
        policyMap.put(id, policyVO);
    }

    public void addRuleList(String policyCode, List<RuleVO> ruleVOList) {
        ruleListMap.put(policyCode, ruleVOList);
    }

    public RuleVO getRuleVO(String policyCode, int index) {
        return ruleListMap.get(policyCode).get(index);
    }

    public void addHitRuleVO(String policyCode, RuleVO ruleVO) {
        if (!hitRuleListMap.containsKey(policyCode)) {
            hitRuleListMap.put(policyCode, CollUtil.newArrayList());
        }
        hitRuleListMap.get(policyCode).add(ruleVO);
    }

    public PolicySetResult convert() {
        PolicySetResult policySetResult = new PolicySetResult(policySetVO.getName(), policySetVO.getCode(), policySetVO.getChain(), policySetVO.getVersion());

        for (Map.Entry<Long, PolicyVO> entry : policyMap.entrySet()) {
            PolicyVO policyVO = entry.getValue();
            PolicyResult policyResult = new PolicyResult(policyVO.getName(), policyVO.getCode(), policyVO.getMode());

            String disposalCode = "pass";
            int maxScore = 0;
            List<RuleVO> ruleVOList = hitRuleListMap.get(policyVO.getCode());
            if (CollUtil.isNotEmpty(ruleVOList)) {
                for (RuleVO ruleVO : ruleVOList) {
                    RuleResult ruleResult = new RuleResult(ruleVO.getName(), ruleVO.getCode(), ruleVO.getScore());

                    Disposal disposal = disposalMap.get(ruleVO.getDisposalCode());
                    if (null != disposal) {
                        ruleResult.setDisposalName(disposal.getName());
                        ruleResult.setDisposalCode(disposal.getCode());
                        if (disposal.getGrade() > maxScore) {
                            maxScore = disposal.getGrade();
                            disposalCode = ruleVO.getDisposalCode();
                        }
                    }
                    // 模拟/正式规则区分开
                    if (RuleStatus.MOCK.equals(ruleVO.getStatus())) {
                        policyResult.addMockRuleResult(ruleResult);
                    } else {
                        policyResult.addRuleResult(ruleResult);
                    }
                }
            }
            // TODO 根据策略模式确定处置结果
            policyResult.setDisposalName(disposalMap.get(disposalCode).getName());
            policyResult.setDisposalCode(disposalMap.get(disposalCode).getCode());
            policySetResult.addPolicyResult(policyResult);
        }
        // TODO
        policySetResult.setDisposalName("通过");
        policySetResult.setDisposalCode("pass");

        return policySetResult;
    }

}
