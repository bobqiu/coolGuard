package cn.wnhyang.coolguard.decision.vo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 4730115285395630037L;

    /**
     * 策略名称
     */
    private String name;

    /**
     * 策略编码
     */
    private String code;

    /**
     * 策略模式
     */
    private String mode;

    /**
     * 处置名称
     */
    private String disposalName;

    /**
     * 处置编码
     */
    private String disposalCode;

    public PolicyResult(String name, String code, String mode) {
        this.name = name;
        this.code = code;
        this.mode = mode;
    }

    /**
     * 正式规则处置结果
     */
    private final List<RuleResult> ruleResults = new ArrayList<>();

    /**
     * 模拟规则处置结果
     */
    private final List<RuleResult> mockRuleResults = new ArrayList<>();

    public void addRuleResult(RuleResult ruleResult) {
        ruleResults.add(ruleResult);
    }

    public void addMockRuleResult(RuleResult ruleResult) {
        mockRuleResults.add(ruleResult);
    }
}
