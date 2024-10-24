package cn.wnhyang.coolGuard.system.vo.core.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/9/12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTree {

    private String name;

    private String path;

    private String component;

    private String redirect;

    private MenuMeta meta;

    private List<MenuTree> children;
}
