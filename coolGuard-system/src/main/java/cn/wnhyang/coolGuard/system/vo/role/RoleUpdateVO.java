package cn.wnhyang.coolGuard.system.vo.role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateVO extends RoleCreateVO {

    /**
     * 角色编号
     */
    @NotNull(message = "角色编号不能为空")
    private Long id;
}
