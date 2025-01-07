package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.Role;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface RoleMapper extends BaseMapperX<Role> {

    default Role selectByName(String name) {
        return selectOne(Role::getName, name);
    }

    default Role selectByValue(String value) {
        return selectOne(Role::getValue, value);
    }

    default PageResult<Role> selectPage(RolePageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Role>()
                .likeIfPresent(Role::getName, reqVO.getName())
                .likeIfPresent(Role::getValue, reqVO.getValue())
                .betweenIfPresent(Role::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime()));
    }
}
