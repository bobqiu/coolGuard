package cn.wnhyang.coolguard.system.vo.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @author wnhyang
 * @date 2023/8/3
 **/
@Data
public class UserUpdateStatusVO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户编号不能为空")
    private Long id;

    /**
     * 帐号状态（0正常 1停用）
     */
    @NotNull(message = "帐号状态不能为空")
    private Boolean status;
}
