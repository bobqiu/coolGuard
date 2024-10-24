package cn.wnhyang.coolGuard.system.vo.core.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author wnhyang
 * @date 2024/9/10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO {

    /**
     * 用户名
     */
    @Size(min = 4, max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;
}
