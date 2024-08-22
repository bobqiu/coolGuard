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

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String name;

    @NotBlank(message = "角色标志不能为空")
    @Size(max = 100, message = "角色标志长度不能超过100个字符")
    private String value;

    private Integer sort;

    private Boolean status;

    private String remark;

    /**
     * 菜单ids
     */
    private Set<Long> menuIds;
}
