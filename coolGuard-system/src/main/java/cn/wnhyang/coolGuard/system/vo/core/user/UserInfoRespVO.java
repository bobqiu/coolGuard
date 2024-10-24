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

    private Long userId;

    private String username;

    private String avatar;

    private String realName;

    private String desc;

    private String token;

    private Set<String> roles;
}
