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
     * 处置名称
     */
    @Setter
    private String disposalName;

    /**
     * 处置编码
     */
    @Setter
    private String disposalCode;

    public PolicySetResult(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * 策略集处置结果
     */
    private final List<PolicyResult> policyResultList = new CopyOnWriteArrayList<>();

    public void addPolicyResult(PolicyResult policyResult) {
        policyResultList.add(policyResult);
    }

}
