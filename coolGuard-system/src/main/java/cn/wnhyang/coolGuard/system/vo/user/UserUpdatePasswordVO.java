package cn.wnhyang.coolGuard.system.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2023/8/3
 **/
@Data
public class UserUpdatePasswordVO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户编号不能为空")
    private Long id;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;
}
