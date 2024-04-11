package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.ServiceConfigVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigCreateVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Mapper
public interface ServiceConfigConvert {

    ServiceConfigConvert INSTANCE = Mappers.getMapper(ServiceConfigConvert.class);

    ServiceConfig convert(ServiceConfigCreateVO createVO);

    ServiceConfig convert(ServiceConfigUpdateVO updateVO);

    ServiceConfigVO convert(ServiceConfig po);

    PageResult<ServiceConfigVO> convert(PageResult<ServiceConfig> pageResult);
}
