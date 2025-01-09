package cn.wnhyang.coolGuard.system.vo.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * @author wnhyang
 * @date 2023/11/16
 **/
@Data
public class UserRoleVO {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    /**
     * 角色编号数组
     */
    private Set<Long> roleIds;
}
