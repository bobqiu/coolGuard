package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.dto.LoginLogCreateDTO;
import cn.wnhyang.coolguard.system.entity.LoginLogDO;
import cn.wnhyang.coolguard.system.vo.loginlog.LoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/7/25
 **/
@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    LoginLogDO convert(LoginLogCreateDTO reqDTO);

    PageResult<LoginLogVO> convertPage(PageResult<LoginLogDO> page);
}
