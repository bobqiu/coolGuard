package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serializable;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategyCreateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * chain名
     */
    private String chainName;

    /**
     * 策略集id
     */
    private Long strategySetId;

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
     * 策略状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}
