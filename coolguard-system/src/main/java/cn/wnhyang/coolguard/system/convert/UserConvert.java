package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.dto.UserCreateDTO;
import cn.wnhyang.coolguard.system.entity.UserDO;
import cn.wnhyang.coolguard.system.login.LoginUser;
import cn.wnhyang.coolguard.system.vo.user.UserCreateVO;
import cn.wnhyang.coolguard.system.vo.user.UserRespVO;
import cn.wnhyang.coolguard.system.vo.user.UserUpdateVO;
import cn.wnhyang.coolguard.system.vo.userprofile.UserProfileUpdateVO;
import cn.wnhyang.coolguard.system.vo.userprofile.UserProfileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/7/26
 **/
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserRespVO convert02(UserDO userDO);

    LoginUser convert(UserDO userDO);

    UserDO convert(UserCreateDTO reqDTO);

    UserDO convert(UserCreateVO reqVO);

    UserDO convert(UserUpdateVO reqVO);

    PageResult<UserRespVO> convert(PageResult<UserDO> pageResult);

    UserProfileVO convert04(UserDO userDO);

    UserDO convert(UserProfileUpdateVO reqVO);
}
