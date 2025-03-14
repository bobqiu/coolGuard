package cn.wnhyang.coolguard.decision.dto;

import cn.wnhyang.coolguard.decision.entity.Th;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Data
public class PolicyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略集编码
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
     * 阈值表
     */
    private List<Th> thList;

    /**
     * 描述
     */
    private String description;

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

    /**
     * 发布
     */
    private Boolean publish;
}
