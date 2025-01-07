package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.io.Serial;
import java.util.List;

/**
 * 接入表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_access", autoResultMap = true)
public class Access extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 显示服务名
     */
    @TableField("display_name")
    private String displayName;

    /**
     * 服务标识
     */
    @TableField("name")
    private String name;

    /**
     * 输入配置
     */
    @TableField(value = "input_config", typeHandler = JacksonTypeHandler.class)
    private List<ConfigField> inputConfig;

    /**
     * 输出配置
     */
    @TableField(value = "output_config", typeHandler = JacksonTypeHandler.class)
    private List<ConfigField> outputConfig;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
