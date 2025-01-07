package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.system.entity.Menu;
import cn.wnhyang.coolGuard.system.vo.menu.MenuListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface MenuMapper extends BaseMapperX<Menu> {

    default Menu selectByParentIdAndName(Long parentId, String name) {
        return selectOne(Menu::getParentId, parentId, Menu::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(Menu::getParentId, parentId);
    }

    default List<Menu> selectList(MenuListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<Menu>()
                .likeIfPresent(Menu::getTitle, reqVO.getTitle()));
    }
}
