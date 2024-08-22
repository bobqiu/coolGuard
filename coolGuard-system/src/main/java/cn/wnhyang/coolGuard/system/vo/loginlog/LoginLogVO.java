package cn.wnhyang.coolGuard.system.vo.loginlog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/15
 **/
@Data
public class LoginLogVO {

    private Long id;

    private Long userId;

    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    private LocalDateTime createTime;

    @NotNull(message = "日志类型不能为空")
    private Integer loginType;

    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String account;

    @NotNull(message = "登录结果不能为空")
    private Integer result;

    @NotEmpty(message = "用户 IP 不能为空")
    private String userIp;

    private String userAgent;
}
