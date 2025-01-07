package cn.wnhyang.coolGuard.system.convert;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.Dict;
import cn.wnhyang.coolGuard.system.vo.dict.DictCreateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictUpdateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Mapper
public interface DictConvert {

    DictConvert INSTANCE = Mappers.getMapper(DictConvert.class);

    Dict convert(DictCreateVO createVO);

    Dict convert(DictUpdateVO updateVO);

    DictVO convert(Dict po);

    PageResult<DictVO> convert(PageResult<Dict> pageResult);

}
