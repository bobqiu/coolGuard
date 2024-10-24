package cn.wnhyang.coolGuard.system.vo.core.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <a href="https://doc.vben.pro/guide/essentials/route.html">...</a>
 *
 * @author wnhyang
 * @date 2024/9/12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuMeta {

    /**
     * 用于配置页面的标题，会在菜单和标签页中显示。一般会配合国际化使用。
     */
    private String title;

    /**
     * 用于配置页面的图标，会在菜单和标签页中显示。一般会配合图标库使用，如果是http链接，会自动加载图片。
     */
    private String icon;

    /**
     * 用于配置页面是否开启缓存，开启后页面会缓存，不会重新加载，仅在标签页启用时有效。
     */
    private Boolean keepalive;

    /**
     * 用于配置页面是否在菜单中隐藏，隐藏后页面不会在菜单中显示。
     */
    private Boolean hideInMenu;

    /**
     * 用于配置页面是否在标签页中隐藏，隐藏后页面不会在标签页中显示。
     */
    private Boolean hideInTab;

    /**
     * 用于配置页面是否在面包屑中隐藏，隐藏后页面不会在面包屑中显示。
     */
    private Boolean hideInBreadcrumb;

    /**
     * 用于配置页面的子页面是否在菜单中隐藏，隐藏后子页面不会在菜单中显示。
     */
    private Boolean hideChildrenInMenu;

    /**
     * 用于配置页面的权限，只有拥有对应权限的用户才能访问页面，不配置则不需要权限。
     */
    private List<String> authority;

    /**
     * 用于配置页面是否固定标签页，固定后页面不可关闭。
     */
    private Boolean affixTab;

    /**
     * 用于配置页面固定标签页的排序, 采用升序排序。
     */
    private Integer affixTabOrder;

    /**
     * 用于配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面。
     */
    private String iframeSrc;

    /**
     * 用于配置页面是否忽略权限，直接可以访问。
     */
    private Boolean ignoreAccess;

    /**
     * 用于配置外链跳转路径，会在新窗口打开。
     */
    private String link;

    /**
     * 用于配置页面的排序，用于路由到菜单排序。
     */
    private Integer order;
}
