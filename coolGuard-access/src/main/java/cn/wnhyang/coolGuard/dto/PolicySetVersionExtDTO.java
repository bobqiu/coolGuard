package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 策略集版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicySetVersionExtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略流
     */
    private String policyFlow;

    /**
     * 规则信息
     */
    private String ruleInfo;
}
