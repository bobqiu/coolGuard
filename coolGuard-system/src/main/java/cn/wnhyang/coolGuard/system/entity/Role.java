package cn.wnhyang.coolGuard.system.entity;

import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;

/**
 * 角色信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class Role extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 6734459350185846076L;

    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色权限字符串
     */
    @TableField("value")
    private String value;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, value);
    }
}
