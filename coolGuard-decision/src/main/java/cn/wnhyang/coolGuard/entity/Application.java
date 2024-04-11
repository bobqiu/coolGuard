package cn.wnhyang.coolGuard.entity;


import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_application")
public class Application extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 显示名
     */
    @TableField("display_name")
    private String displayName;

    /**
     * 应用名
     */
    @TableField("name")
    private String name;

    /**
     * 密钥
     */
    @TableField("secret")
    private String secret;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
