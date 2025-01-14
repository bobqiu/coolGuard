package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.UserDO;
import cn.wnhyang.coolGuard.system.vo.user.UserPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {

    default UserDO selectByUsername(String username) {
        return selectOne(UserDO::getUsername, username);
    }

    default PageResult<UserDO> selectPage(UserPageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getUsername, reqVO.getUsername())
                .likeIfPresent(UserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(UserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(UserDO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(UserDO::getId));
    }

    default UserDO selectByMobile(String mobile) {
        return selectOne(UserDO::getMobile, mobile);
    }

    default UserDO selectByEmail(String email) {
        return selectOne(UserDO::getEmail, email);
    }

    default List<UserDO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<UserDO>().like(UserDO::getNickname, nickname));
    }
}
