package cn.wnhyang.coolGuard.system.convert;


import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.UserCreateDTO;
import cn.wnhyang.coolGuard.system.entity.Role;
import cn.wnhyang.coolGuard.system.entity.User;
import cn.wnhyang.coolGuard.system.login.LoginUser;
import cn.wnhyang.coolGuard.system.vo.user.UserCreateVO;
import cn.wnhyang.coolGuard.system.vo.user.UserRespVO;
import cn.wnhyang.coolGuard.system.vo.user.UserUpdateVO;
import cn.wnhyang.coolGuard.system.vo.userprofile.UserProfileUpdateVO;
import cn.wnhyang.coolGuard.system.vo.userprofile.UserProfileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wnhyang
 * @date 2023/7/26
 **/
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserRespVO convert02(User userDO);

    default UserRespVO convert(User userDO, List<Role> roleDOList) {
        UserRespVO userRespVO = convert02(userDO);
        if (CollUtil.isNotEmpty(roleDOList)) {
            Set<String> roleNames = roleDOList.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            userRespVO.setRoles(roleNames);
        }
        return userRespVO;
    }

    LoginUser convert(User userDO);

    User convert(UserCreateDTO reqDTO);

    User convert(UserCreateVO reqVO);

    User convert(UserUpdateVO reqVO);

    PageResult<UserRespVO> convert(PageResult<User> pageResult);

    UserProfileVO convert04(User user);

    User convert(UserProfileUpdateVO reqVO);
}
