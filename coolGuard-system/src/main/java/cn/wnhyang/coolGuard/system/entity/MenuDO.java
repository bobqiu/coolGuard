package cn.wnhyang.coolGuard.system.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.io.Serial;
import java.util.Map;

/**
 * 菜单权限表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_menu", autoResultMap = true)
public class MenuDO extends BaseDO {

    /**
     * 菜单编号 - 根节点
     */
    public static final Long ID_ROOT = 0L;

    @Serial
    private static final long serialVersionUID = 986081501377397378L;

    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;

    /**
     * 菜单类型 目录0 菜单1 按钮2
     */
    @TableField("type")
    private Integer type;

    /**
     * 菜单名称 作为唯一索引
     */
    @TableField("name")
    private String name;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 当设置 noredirect 的时候该路由在面包屑导航中不可被点击
     */
    @TableField("redirect")
    private String redirect;

    // ----- routeMeta start -----

    /**
     * 数据库关键字
     * order 改为 order_no
     * 用于路由->菜单排序
     */
    @TableField("order_no")
    private Integer order;

    /**
     * 数据库关键字
     * query 改为 query_param
     * 菜单所携带的参数
     */
    @TableField(value = "query_param", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> query;

    /**
     * 标题名称
     */
    @TableField("title")
    private String title;


    // ----- routeMeta end -----

    @TableField(value = "meta", typeHandler = JacksonTypeHandler.class)
    private RouteMeta meta;

}
