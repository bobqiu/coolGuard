package cn.wnhyang.coolGuard.system.vo.role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
public class RoleUpdateStatusVO {

    @NotNull(message = "角色编号不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private Boolean status;
}
