package cn.wnhyang.coolGuard.system.vo.menu;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuUpdateVO extends MenuCreateVO {

    @NotNull(message = "菜单编号不能为空")
    private Long id;
}
