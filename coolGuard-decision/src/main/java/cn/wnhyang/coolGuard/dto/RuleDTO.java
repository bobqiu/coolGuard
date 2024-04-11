package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * chain名
     */
    private String chainName;

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
    private String disposalId;

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
