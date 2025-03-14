package cn.wnhyang.coolguard.system.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.entity.ParamDO;
import cn.wnhyang.coolguard.system.vo.param.ParamCreateVO;
import cn.wnhyang.coolguard.system.vo.param.ParamUpdateVO;
import cn.wnhyang.coolguard.system.vo.param.ParamVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Mapper
public interface ParamConvert {

    ParamConvert INSTANCE = Mappers.getMapper(ParamConvert.class);

    ParamDO convert(ParamCreateVO createVO);

    ParamDO convert(ParamUpdateVO updateVO);

    ParamVO convert(ParamDO po);

    PageResult<ParamVO> convert(PageResult<ParamDO> pageResult);

}
