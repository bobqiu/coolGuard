package cn.wnhyang.coolGuard.system.convert;


import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.UserCreateDTO;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.entity.UserDO;
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

    UserRespVO convert02(UserDO userDO);

    default UserRespVO convert(UserDO userDO, List<RoleDO> roleDODOList) {
        UserRespVO userRespVO = convert02(userDO);
        if (CollUtil.isNotEmpty(roleDODOList)) {
            Set<String> roleNames = roleDODOList.stream()
                    .map(RoleDO::getName)
                    .collect(Collectors.toSet());

            userRespVO.setRoles(roleNames);
        }
        return userRespVO;
    }

    LoginUser convert(UserDO userDO);

    UserDO convert(UserCreateDTO reqDTO);

    UserDO convert(UserCreateVO reqVO);

    UserDO convert(UserUpdateVO reqVO);

    PageResult<UserRespVO> convert(PageResult<UserDO> pageResult);

    UserProfileVO convert04(UserDO userDO);

    UserDO convert(UserProfileUpdateVO reqVO);
}
