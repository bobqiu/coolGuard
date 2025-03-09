package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface RoleMapper extends BaseMapperX<RoleDO> {

    default RoleDO selectByName(String name) {
        return selectOne(RoleDO::getName, name);
    }

    default RoleDO selectByCode(String code) {
        return selectOne(RoleDO::getCode, code);
    }

    default PageResult<RoleDO> selectPage(RolePageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoleDO>()
                .likeIfPresent(RoleDO::getName, reqVO.getName())
                .likeIfPresent(RoleDO::getCode, reqVO.getCode()));
    }
}
