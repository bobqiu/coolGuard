package cn.wnhyang.coolGuard.system.vo.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
public class RoleCreateVO {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String name;

    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    @Size(max = 100, message = "角色标识长度不能超过100个字符")
    private String value;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 菜单ids
     */
    private Set<Long> menuIds;
}
