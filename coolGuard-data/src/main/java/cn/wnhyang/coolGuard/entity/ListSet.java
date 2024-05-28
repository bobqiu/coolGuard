package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_list_set")
public class ListSet extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名单集名
     */
    @TableField("name")
    private String name;

    /**
     * 名单集类型
     */
    @TableField("type")
    private String type;

    /**
     * 名单集状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
