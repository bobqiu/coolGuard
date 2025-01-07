package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.DictDataDTO;
import cn.wnhyang.coolGuard.system.entity.DictDataDO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataCreateVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataRespVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataSimpleVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2023/9/14
 **/
@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    DictDataDO convert(DictDataCreateVO reqVO);

    DictDataRespVO convert(DictDataDO bean);

    DictDataDO convert(DictDataUpdateVO reqVO);

    List<DictDataSimpleVO> convertList(List<DictDataDO> list);

    PageResult<DictDataRespVO> convertPage(PageResult<DictDataDO> page);

    DictDataDTO convert02(DictDataDO dictData);
}
