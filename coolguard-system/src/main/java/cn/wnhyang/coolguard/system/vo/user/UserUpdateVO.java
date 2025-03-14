package cn.wnhyang.coolguard.system.vo.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/8/3
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateVO extends UserCreateVO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户编号不能为空")
    private Long id;
}
