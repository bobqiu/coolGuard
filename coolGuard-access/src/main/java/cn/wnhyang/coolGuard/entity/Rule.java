package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_rule", autoResultMap = true)
public class Rule extends BasePO {

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
     * 分数
     */
    @TableField("score")
    private Integer score;

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
     * 描述
     */
    @TableField("description")
    private String description;
}
