package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_field_ref")
public class FieldRef extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 被引用类型
     */
    @TableField("ref_type")
    private String refType;

    /**
     * 被引用
     */
    @TableField("ref_by")
    private String refBy;

    /**
     * 被引用子类型
     */
    @TableField("ref_sub_type")
    private String refSubType;

    /**
     * 字段编码
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 必须
     */
    @TableField("required")
    private Boolean required;

    /**
     * 参数名
     */
    @TableField("param_name")
    private String paramName;
}
