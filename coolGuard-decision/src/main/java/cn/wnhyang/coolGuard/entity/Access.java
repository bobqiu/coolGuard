package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 服务配置表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_access")
public class Access extends BasePO {

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
    @TableField("input_config")
    private String inputConfig;

    /**
     * 输出配置
     */
    @TableField("output_config")
    private String outputConfig;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
