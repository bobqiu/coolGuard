package cn.wnhyang.coolGuard.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Getter
public class PolicyResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 4730115285395630037L;

    /**
     * 策略名称
     */
    private final String name;

    /**
     * 策略编码
     */
    private final String code;

    /**
     * 策略模式
     */
    private final String mode;

    /**
     * 处置名称
     */
    @Setter
    private String disposalName;

    /**
     * 处置编码
     */
    @Setter
    private String disposalCode;

    public PolicyResult(String name, String code, String mode) {
        this.name = name;
        this.code = code;
        this.mode = mode;
    }

    /**
     * 规则处置结果
     */
    private final List<RuleResult> ruleResultList = new CopyOnWriteArrayList<>();

    public void addRuleResult(RuleResult ruleResult) {
        ruleResultList.add(ruleResult);
    }
}
