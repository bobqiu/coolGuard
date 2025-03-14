package cn.wnhyang.coolguard.system.mapper;

import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolguard.system.entity.MenuDO;
import cn.wnhyang.coolguard.system.vo.menu.MenuListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default List<MenuDO> selectListOrder() {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .orderByAsc(MenuDO::getOrder));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }

    default List<MenuDO> selectListOrder(MenuListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .likeIfPresent(MenuDO::getTitle, reqVO.getTitle())
                .orderByAsc(MenuDO::getOrder));
    }

    default MenuDO selectByName(String name) {
        return selectOne(MenuDO::getName, name);
    }

    default MenuDO selectByPath(String path) {
        return selectOne(MenuDO::getPath, path);
    }
}
