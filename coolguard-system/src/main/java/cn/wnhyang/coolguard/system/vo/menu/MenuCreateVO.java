package cn.wnhyang.coolguard.system.vo.menu;

import cn.wnhyang.coolguard.system.entity.RouteMeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Data
public class MenuCreateVO {

    /**
     * 菜单类型
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单组件，根组件默认为BasicLayout
     */
    private String component;

    /**
     * 菜单重定向地址
     */
    private String redirect;

    /**
     * 菜单权限
     */
    private String permission;

    /**
     * 父菜单 ID
     */
    @NotNull(message = "父菜单 ID 不能为空")
    private Long parentId;

    /**
     * 路由元信息
     */
    private RouteMeta meta;

    /**
     * 数据库关键字
     * order 改为 order_no
     * 用于路由->菜单排序
     */
    private Integer order;

    /**
     * 数据库关键字
     * query 改为 query_param
     * 菜单所携带的参数
     */
    private Map<String, Object> query;

    /**
     * 标题名称
     */
    private String title;

}
