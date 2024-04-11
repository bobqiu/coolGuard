package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serializable;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategySetCreateVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 策略集状态
     */
    private Boolean status;

    /**
     * 描述
     */
    private String description;
}
