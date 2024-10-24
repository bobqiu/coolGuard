package cn.wnhyang.coolGuard.system.vo.core.auth;

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

    private Long userId;

    private String username;

    private String realName;

    private String desc;

    private String accessToken;
}
