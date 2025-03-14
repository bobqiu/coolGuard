package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.entity.DictTypeDO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeCreateVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeRespVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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

}
