package cn.wnhyang.coolGuard.system.vo.userprofile;

import cn.wnhyang.coolGuard.system.vo.user.UserCreateVO;
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

    private Long id;

    private String loginIp;

    private LocalDateTime loginDate;

    private LocalDateTime createTime;
}
