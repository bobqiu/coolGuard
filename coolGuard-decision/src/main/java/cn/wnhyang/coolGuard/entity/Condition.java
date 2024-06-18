package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_condition")
public class Condition extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 条件类型
     */
    @TableField("type")
    private String type;

    /**
     * 操作对象
     */
    @TableField("value")
    private String value;

    /**
     * 操作类型
     */
    @TableField("logic_type")
    private String logicType;

    /**
     * 期望类型
     */
    @TableField("expect_type")
    private String expectedType;

    /**
     * 期望值
     */
    @TableField("expect_value")
    private String expectValue;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
