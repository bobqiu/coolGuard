package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.ServiceConfigField;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.ServiceConfigFieldVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigFieldCreateVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigFieldUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 服务配置字段表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ServiceConfigFieldConvert {

    ServiceConfigFieldConvert INSTANCE = Mappers.getMapper(ServiceConfigFieldConvert.class);

    ServiceConfigField convert(ServiceConfigFieldCreateVO createVO);

    ServiceConfigField convert(ServiceConfigFieldUpdateVO updateVO);

    ServiceConfigFieldVO convert(ServiceConfigField po);

    PageResult<ServiceConfigFieldVO> convert(PageResult<ServiceConfigField> pageResult);

    List<ServiceConfigField> convert(List<ServiceConfigFieldCreateVO> list);
}
