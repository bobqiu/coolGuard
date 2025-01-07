package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.User;
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
public interface UserMapper extends BaseMapperX<User> {

    default User selectByUsername(String username) {
        return selectOne(User::getUsername, username);
    }

    default PageResult<User> selectPage(UserPageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<User>()
                .likeIfPresent(User::getUsername, reqVO.getUsername())
                .likeIfPresent(User::getMobile, reqVO.getMobile())
                .eqIfPresent(User::getStatus, reqVO.getStatus())
                .betweenIfPresent(User::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(User::getId));
    }

    default User selectByMobile(String mobile) {
        return selectOne(User::getMobile, mobile);
    }

    default User selectByEmail(String email) {
        return selectOne(User::getEmail, email);
    }

    default List<User> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<User>().like(User::getNickname, nickname));
    }
}
