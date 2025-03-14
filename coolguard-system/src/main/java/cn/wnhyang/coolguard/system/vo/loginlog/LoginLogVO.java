package cn.wnhyang.coolguard.system.vo.loginlog;

import jakarta.validation.constraints.NotBlank;
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

    /**
     * id
     */
    private Long id;

    /**
     * ip
     */
    private Long userId;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 日志类型
     */
    @NotNull(message = "日志类型不能为空")
    private Integer loginType;

    /**
     * 账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String account;

    /**
     * 结果
     */
    @NotNull(message = "登录结果不能为空")
    private Integer result;

    /**
     * ip
     */
    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    /**
     * 用户代理
     */
    private String userAgent;
}
