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
public class PolicySetResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 2469991094328679575L;

    /**
     * 策略名称
     */
    private String name;

    /**
     * 策略集编码
     */
    private String code;

    /**
     * 策略集链
     */
    private String chain;

    /**
     * 策略集版本
     */
    private Integer version;

    /**
     * 处置名称
     */
    private String disposalName;

    /**
     * 处置编码
     */
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
