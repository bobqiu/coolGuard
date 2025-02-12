package cn.wnhyang.coolGuard.dto;

import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.Cond;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class RuleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略编码
     */
    private String policyCode;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 规则id
     */
    private String ruleId;

    /**
     * 规则名
     */
    private String name;

    /**
     * 处理编码
     */
    private String disposalCode;

    /**
     * 表达式
     */
    private String express;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 条件
     */
    private Cond cond;

    /**
     * true执行
     */
    private Action ruleTrue;

    /**
     * false执行
     */
    private Action ruleFalse;

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
