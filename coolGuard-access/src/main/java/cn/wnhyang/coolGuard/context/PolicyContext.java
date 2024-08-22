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

    private final Map<Long, Disposal> disposalMap = new ConcurrentHashMap<>();

    private PolicySetVO policySetVO;

    private final Map<Long, PolicyVO> policyMap = new ConcurrentHashMap<>();

    private final Map<Long, List<RuleVO>> ruleListMap = new ConcurrentHashMap<>();

    private final Map<Long, List<RuleVO>> hitRuleListMap = new ConcurrentHashMap<>();

    public void addDisposal(long id, Disposal disposal) {
        disposalMap.put(id, disposal);
    }

    public void addPolicy(long id, PolicyVO policyVO) {
        policyMap.put(id, policyVO);
    }

    public void addRuleList(long id, List<RuleVO> ruleVOList) {
        ruleListMap.put(id, ruleVOList);
    }

    public RuleVO getRuleVO(long policyId, int index) {
        return ruleListMap.get(policyId).get(index);
    }

    public void addHitRuleVO(long id, RuleVO ruleVO) {
        if (!hitRuleListMap.containsKey(id)) {
            hitRuleListMap.put(id, CollUtil.newArrayList());
        }
        hitRuleListMap.get(id).add(ruleVO);
    }

    public PolicySetResult convert() {
        PolicySetResult policySetResult = new PolicySetResult(policySetVO.getName(), policySetVO.getCode());

        for (Map.Entry<Long, PolicyVO> entry : policyMap.entrySet()) {
            PolicyVO policyVO = entry.getValue();
            PolicyResult policyResult = new PolicyResult(policyVO.getName(), policyVO.getCode(), policyVO.getMode());

            long disposalId = 1L;
            int maxScore = 0;
            List<RuleVO> ruleVOList = hitRuleListMap.get(policyVO.getId());
            if (CollUtil.isNotEmpty(ruleVOList)) {
                for (RuleVO ruleVO : ruleVOList) {
                    RuleResult ruleResult = new RuleResult(ruleVO.getName(), ruleVO.getCode(), ruleVO.getScore());

                    Disposal disposal = disposalMap.get(ruleVO.getDisposalId());
                    if (null != disposal) {
                        ruleResult.setDisposalName(disposal.getName());
                        ruleResult.setDisposalCode(disposal.getCode());
                        if (disposal.getGrade() > maxScore) {
                            maxScore = disposal.getGrade();
                            disposalId = ruleVO.getDisposalId();
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
            policyResult.setDisposalName(disposalMap.get(disposalId).getName());
            policyResult.setDisposalCode(disposalMap.get(disposalId).getCode());
            policySetResult.addPolicyResult(policyResult);
        }
        // TODO
        policySetResult.setDisposalName("通过");
        policySetResult.setDisposalCode("pass");

        return policySetResult;
    }

}
