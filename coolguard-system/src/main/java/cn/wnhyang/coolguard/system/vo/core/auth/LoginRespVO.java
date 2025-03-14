package cn.wnhyang.coolguard.system.vo.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2024/9/10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 访问令牌
     */
    private String accessToken;
}
