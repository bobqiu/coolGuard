package cn.wnhyang.coolGuard.system.vo.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * @author wnhyang
 * @date 2023/11/15
 **/
@Data
public class RoleMenuVO {

    /**
     * 角色编号
     */
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    /**
     * 菜单编号
     */
    private Set<Long> menuIds;
}
