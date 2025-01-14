package cn.wnhyang.coolGuard.system.convert;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictDO;
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

    DictDO convert(DictCreateVO createVO);

    DictDO convert(DictUpdateVO updateVO);

    DictVO convert(DictDO po);

    PageResult<DictVO> convert(PageResult<DictDO> pageResult);

}
