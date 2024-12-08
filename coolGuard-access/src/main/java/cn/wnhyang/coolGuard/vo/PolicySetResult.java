package cn.wnhyang.coolGuard.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Getter
public class PolicySetResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 2469991094328679575L;

    /**
     * 策略名称
     */
    private final String name;

    /**
     * 策略集编码
     */
    private final String code;

    /**
     * 策略集链
     */
    private final String chain;

    /**
     * 策略集版本
     */
    private final Integer version;

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

    public PolicySetResult(String name, String code, String chain, Integer version) {
        this.name = name;
        this.code = code;
        this.chain = chain;
        this.version = version;
    }

    /**
     * 策略集处置结果
     */
    private final List<PolicyResult> policyResults = new ArrayList<>();

    public void addPolicyResult(PolicyResult policyResult) {
        policyResults.add(policyResult);
    }

}
