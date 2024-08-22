package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.RolePO;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface RoleMapper extends BaseMapperX<RolePO> {

    default RolePO selectByName(String name) {
        return selectOne(RolePO::getName, name);
    }

    default RolePO selectByValue(String value) {
        return selectOne(RolePO::getValue, value);
    }

    default PageResult<RolePO> selectPage(RolePageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RolePO>()
                .likeIfPresent(RolePO::getName, reqVO.getName())
                .likeIfPresent(RolePO::getValue, reqVO.getValue())
                .eqIfPresent(RolePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RolePO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(RolePO::getId));
    }
}
