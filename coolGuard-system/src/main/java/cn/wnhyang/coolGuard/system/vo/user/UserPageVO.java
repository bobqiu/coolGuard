package cn.wnhyang.coolGuard.system.vo.user;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @author wnhyang
 * @date 2023/8/9
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1382984856072227773L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 账号过期时间
     */
    private LocalDate expireDate;

}
