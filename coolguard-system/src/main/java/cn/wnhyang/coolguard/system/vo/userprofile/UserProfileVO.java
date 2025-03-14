package cn.wnhyang.coolguard.system.vo.userprofile;

import cn.wnhyang.coolguard.system.vo.user.UserCreateVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/11/23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProfileVO extends UserCreateVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 结束时间
     */
    private LocalDateTime createTime;
}
