package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.system.entity.RoleMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 角色和菜单关联表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenu> {

    default List<RoleMenu> selectListByRoleId(Collection<Long> roleIds) {
        return selectList(RoleMenu::getRoleId, roleIds);
    }

    default void deleteByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }

    default Long selectCountByMenuId(Long menuId) {
        return selectCount(RoleMenu::getMenuId, menuId);
    }

    default List<RoleMenu> selectListByRoleId(Long roleId) {
        return selectList(RoleMenu::getRoleId, roleId);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId)
                .in(RoleMenu::getMenuId, menuIds));
    }
}
