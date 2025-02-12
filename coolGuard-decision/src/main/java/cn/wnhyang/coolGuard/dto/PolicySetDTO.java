package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 策略集表版本
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Data
public class PolicySetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 策略集链
     */
    private String chain;

    /**
     * 描述
     */
    private String description;

    /**
     * 发布
     */
    private Boolean publish;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 版本描述
     */
    private String versionDesc;
}
