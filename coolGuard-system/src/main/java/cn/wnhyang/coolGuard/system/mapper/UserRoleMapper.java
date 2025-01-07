package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.system.entity.UserRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 用户和角色关联表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRole> {

    default List<UserRole> selectListByUserId(Collection<Long> userIds) {
        return selectList(UserRole::getUserId, userIds);
    }

    default void deleteByUserId(Long userId) {
        delete(Wrappers.lambdaUpdate(UserRole.class).eq(UserRole::getUserId, userId));
    }

    default List<UserRole> selectListByUserId(Long userId) {
        return selectList(UserRole::getUserId, userId);
    }

    default void deleteListByUserIdAndRoleIdIds(Long userId, Collection<Long> roleIds) {
        delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .in(UserRole::getRoleId, roleIds));
    }

    default Long selectCountByRoleId(Long roleId) {
        return selectCount(UserRole::getRoleId, roleId);
    }
}
