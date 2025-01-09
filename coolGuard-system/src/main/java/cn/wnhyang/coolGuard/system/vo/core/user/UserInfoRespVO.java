package cn.wnhyang.coolGuard.system.vo.core.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author wnhyang
 * @date 2024/9/12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRespVO {

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 描述
     */
    private String desc;

    /**
     * token
     */
    private String token;

    /**
     * 角色
     */
    private Set<String> roles;
}
