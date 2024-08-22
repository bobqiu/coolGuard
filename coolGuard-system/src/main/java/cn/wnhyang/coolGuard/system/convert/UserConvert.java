package cn.wnhyang.coolGuard.system.convert;


import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.UserCreateDTO;
import cn.wnhyang.coolGuard.system.entity.RolePO;
import cn.wnhyang.coolGuard.system.entity.UserPO;
import cn.wnhyang.coolGuard.system.login.LoginUser;
import cn.wnhyang.coolGuard.system.vo.user.UserCreateVO;
import cn.wnhyang.coolGuard.system.vo.user.UserInfoVO;
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

    UserRespVO convert02(UserPO userDO);

    default UserRespVO convert(UserPO userDO, List<RolePO> roleDOList) {
        UserRespVO userRespVO = convert02(userDO);
        if (CollUtil.isNotEmpty(roleDOList)) {
            Set<String> roleNames = roleDOList.stream()
                    .map(RolePO::getName)
                    .collect(Collectors.toSet());

            userRespVO.setRoles(roleNames);
        }
        return userRespVO;
    }

    LoginUser convert(UserPO userDO);

    UserPO convert(UserCreateDTO reqDTO);

    UserPO convert(UserCreateVO reqVO);

    UserPO convert(UserUpdateVO reqVO);

    PageResult<UserRespVO> convert(PageResult<UserPO> pageResult);

    UserInfoVO.UserVO convert03(UserPO user);

    UserProfileVO convert04(UserPO user);

    UserPO convert(UserProfileUpdateVO reqVO);
}
