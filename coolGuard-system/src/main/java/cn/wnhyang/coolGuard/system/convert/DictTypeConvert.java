package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictTypeDO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypeCreateVO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypeRespVO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypeSimpleVO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypeUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2023/9/13
 **/
@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    DictTypeDO convert(DictTypeCreateVO reqVO);

    DictTypeDO convert(DictTypeUpdateVO reqVO);

    PageResult<DictTypeRespVO> convertPage(PageResult<DictTypeDO> dictTypePage);

    DictTypeRespVO convert(DictTypeDO dictType);

    List<DictTypeSimpleVO> convertList(List<DictTypeDO> list);
}
