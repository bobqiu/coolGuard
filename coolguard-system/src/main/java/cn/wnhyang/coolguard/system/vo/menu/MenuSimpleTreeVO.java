package cn.wnhyang.coolguard.system.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2023/9/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuSimpleTreeVO {

    /**
     * 菜单编号
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 父菜单编号
     */
    private Long parentId;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 子菜单
     */
    private List<MenuSimpleTreeVO> children;

}
