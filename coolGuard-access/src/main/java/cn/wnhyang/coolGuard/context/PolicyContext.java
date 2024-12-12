package cn.wnhyang.coolGuard.context;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.Cond;
import cn.wnhyang.coolGuard.vo.result.PolicyResult;
import cn.wnhyang.coolGuard.vo.result.PolicySetResult;
import cn.wnhyang.coolGuard.vo.result.RuleResult;
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

    private final Map<String, DisposalCtx> disposalMap = new ConcurrentHashMap<>();

    private PolicySetCtx policySet;

    private final Map<String, PolicyCtx> policyMap = new ConcurrentHashMap<>();

    private final Map<String, List<RuleCtx>> ruleListMap = new ConcurrentHashMap<>();

    private final Map<String, List<RuleCtx>> hitRuleListMap = new ConcurrentHashMap<>();

    public void init(List<DisposalCtx> disposalCtxList) {
        for (DisposalCtx disposalCtx : disposalCtxList) {
            disposalMap.put(disposalCtx.getCode(), disposalCtx);
        }
    }

    public void addPolicy(String policyCode, PolicyCtx policy) {
        policyMap.put(policyCode, policy);
    }

    public void addRuleList(String policyCode, List<RuleCtx> ruleList) {
        ruleListMap.put(policyCode, ruleList);
    }

    public RuleCtx getRule(String policyCode, int index) {
        return ruleListMap.get(policyCode).get(index);
    }

    public void addHitRuleVO(String policyCode, RuleCtx rule) {
        if (!hitRuleListMap.containsKey(policyCode)) {
            hitRuleListMap.put(policyCode, CollUtil.newArrayList());
        }
        hitRuleListMap.get(policyCode).add(rule);
    }

    public PolicySetResult convert() {
        PolicySetResult policySetResult = new PolicySetResult(policySet.getName(), policySet.getCode(), policySet.getChain(), policySet.getVersion());

        for (Map.Entry<String, PolicyCtx> entry : policyMap.entrySet()) {
            PolicyCtx policy = entry.getValue();
            PolicyResult policyResult = new PolicyResult(policy.getName(), policy.getCode(), policy.getMode());

            String disposalCode = "pass";
            int maxScore = 0;
            List<RuleCtx> ruleList = hitRuleListMap.get(policy.getCode());
            if (CollUtil.isNotEmpty(ruleList)) {
                for (RuleCtx rule : ruleList) {
                    RuleResult ruleResult = new RuleResult(rule.getName(), rule.getCode(), rule.getScore());

                    DisposalCtx disposal = disposalMap.get(rule.getDisposalCode());
                    if (null != disposal) {
                        ruleResult.setDisposalName(disposal.getName());
                        ruleResult.setDisposalCode(disposal.getCode());
                        if (disposal.getGrade() > maxScore) {
                            maxScore = disposal.getGrade();
                            disposalCode = rule.getDisposalCode();
                        }
                    }
                    // 模拟/正式规则区分开
                    if (RuleStatus.MOCK.equals(rule.getStatus())) {
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

    @Data
    public static class PolicySetCtx {

        /**
         * 主键
         */
        private Long id;

        /**
         * app名
         */
        private String appName;

        /**
         * 策略集编码
         */
        private String code;

        /**
         * 策略集名
         */
        private String name;

        /**
         * 描述
         */
        private String description;

        /**
         * 策略集链
         */
        private String chain;

        /**
         * 发布
         */
        private Boolean publish;

        /**
         * 版本
         */
        private Integer version;
    }

    @Data
    public static class PolicyCtx {

        /**
         * 主键
         */
        private Long id;

        /**
         * 策略集id
         */
        private String policySetCode;

        /**
         * 策略编码
         */
        private String code;

        /**
         * 策略模式
         */
        private String mode;

        /**
         * 策略名
         */
        private String name;

        /**
         * 描述
         */
        private String description;
    }

    @Data
    public static class RuleCtx {

        /**
         * 主键
         */
        private Long id;

        /**
         * 策略编码
         */
        private String policyCode;

        /**
         * 规则编码
         */
        private String code;

        /**
         * 规则名
         */
        private String name;

        /**
         * 处理编码
         */
        private String disposalCode;

        /**
         * 分数
         */
        private Integer score;

        /**
         * 状态
         */
        private String status;

        /**
         * 排序
         */
        private Integer sort;

        /**
         * 描述
         */
        private String description;

        /**
         * 条件
         */
        private Cond cond;

        /**
         * true执行
         */
        private Action ruleTrue;

        /**
         * false执行
         */
        private Action ruleFalse;
    }

    @Data
    public static class DisposalCtx {

        /**
         * 主键
         */
        private Long id;

        /**
         * 处置编码
         */
        private String code;

        /**
         * 处置名
         */
        private String name;

        /**
         * 等级
         */
        private Integer grade;

        /**
         * 颜色
         */
        private String color;

        /**
         * 是否为标准
         */
        private Boolean standard;

        /**
         * 描述
         */
        private String description;
    }

}
