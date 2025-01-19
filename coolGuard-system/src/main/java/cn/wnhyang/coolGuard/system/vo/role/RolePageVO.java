package cn.wnhyang.coolGuard.system.vo.role;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -5653242641955460431L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     */
    private String code;

}
