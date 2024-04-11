package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 服务配置字段表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_service_config_field")
public class ServiceConfigField extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 服务id
     */
    @TableField("service_config_id")
    private Long serviceConfigId;

    /**
     * 参数名
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 是否必须
     */
    @TableField("required")
    private Boolean required;

    /**
     * 字段id
     */
    @TableField("field_name")
    private String fieldName;
}
