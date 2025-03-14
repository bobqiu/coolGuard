package cn.wnhyang.coolguard.system.vo.userprofile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2023/11/23
 **/
@Data
public class UserProfileUpdateVO {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过 30 个字符")
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号长度必须 11 位")
    private String mobile;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 备注
     */
    private String remark;
}
