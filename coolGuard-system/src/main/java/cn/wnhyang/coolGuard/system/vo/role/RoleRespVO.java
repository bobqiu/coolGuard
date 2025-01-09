package cn.wnhyang.coolGuard.system.vo.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleRespVO extends RoleCreateVO {

    /**
     * 角色编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
