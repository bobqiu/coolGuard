package cn.wnhyang.coolGuard.decision.entity;

import cn.wnhyang.coolGuard.common.LabelValueAble;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_rule", autoResultMap = true)
public class Rule extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略编码
     */
    @TableField("policy_code")
    private String policyCode;

    /**
     * 规则编码
     */
    @TableField("code")
    private String code;

    /**
     * 规则id
     */
    @TableField("rule_id")
    private String ruleId;

    /**
     * 规则名
     */
    @TableField("name")
    private String name;

    /**
     * 处理编码
     */
    @TableField("disposal_code")
    private String disposalCode;

    /**
     * 表达式
     */
    @TableField("express")
    private String express;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 发布
     */
    @TableField("publish")
    private Boolean publish;

    /**
     * 条件
     */
    @TableField(value = "cond", typeHandler = JacksonTypeHandler.class)
    private Cond cond;

    /**
     * true执行
     */
    @TableField(value = "rule_true", typeHandler = JacksonTypeHandler.class)
    private Action ruleTrue;

    /**
     * false执行
     */
    @TableField(value = "rule_false", typeHandler = JacksonTypeHandler.class)
    private Action ruleFalse;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
