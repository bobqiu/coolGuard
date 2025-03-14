package cn.wnhyang.coolguard.system.vo.userprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * @author wnhyang
 * @date 2023/11/23
 **/
@Data
public class UserProfileUpdatePasswordVO {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @Size(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @Size(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String newPassword;
}
