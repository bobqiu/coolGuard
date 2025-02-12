package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.dto.IndicatorDTO;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Mapper
public interface IndicatorConvert {

    IndicatorConvert INSTANCE = Mappers.getMapper(IndicatorConvert.class);

    Indicator convert(IndicatorCreateVO createVO);

    Indicator convert(IndicatorUpdateVO updateVO);

    IndicatorVO convert(Indicator po);

    PageResult<IndicatorVO> convert(PageResult<Indicator> pageResult);

    List<IndicatorVO> convert(List<Indicator> list);

    List<IndicatorVO> convertVersion(List<IndicatorVersion> indicatorVersionList);

    IndicatorUpdateVO convert2Update(IndicatorVO indicatorVO);

    Indicator convert(IndicatorVersion indicatorVersion);

    PageResult<IndicatorVO> convert2(PageResult<IndicatorDTO> pageResult);
}
