package cn.wnhyang.coolGuard.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicyVersionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 策略名
     */
    private String name;

    /**
     * 策略模式
     */
    private String mode;

    /**
     * 策略状态，应该包含开启、关闭、待发布
     */
    private Boolean status;

    /**
     * 策略状态
     */
    private Integer version;

    /**
     * 描述
     */
    private String description;
}
