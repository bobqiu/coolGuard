package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.LoginLogCreateDTO;
import cn.wnhyang.coolGuard.system.entity.LoginLog;
import cn.wnhyang.coolGuard.system.vo.loginlog.LoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/7/25
 **/
@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    LoginLog convert(LoginLogCreateDTO reqDTO);

    PageResult<LoginLogVO> convertPage(PageResult<LoginLog> page);
}
