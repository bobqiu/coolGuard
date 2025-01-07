package cn.wnhyang.coolGuard.system.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_param")
public class Param extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数标签
     */
    @TableField("label")
    private String label;

    /**
     * 参数值
     */
    @TableField("value")
    private String value;

    /**
     * 参数类型
     */
    @TableField("type")
    private String type;

    /**
     * 参数数据
     */
    @TableField("data")
    private String data;

    /**
     * 标准
     */
    @TableField("standard")
    private Boolean standard;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
