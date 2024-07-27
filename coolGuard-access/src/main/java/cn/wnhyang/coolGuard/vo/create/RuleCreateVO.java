package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 策略id
     */
    private Long policyId;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 规则名
     */
    private String name;

    /**
     * 处理编码
     */
    private Long disposalId;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;
}
