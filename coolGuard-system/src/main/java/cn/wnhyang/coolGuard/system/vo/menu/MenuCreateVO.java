package cn.wnhyang.coolGuard.system.vo.menu;

import cn.wnhyang.coolGuard.system.entity.RouteMeta;
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

    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    private String path;

    private String component;

    private String redirect;

    private String permission;

    @NotNull(message = "父菜单 ID 不能为空")
    private Long parentId;

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
