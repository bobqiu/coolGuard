package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.ListData;
import cn.wnhyang.coolguard.decision.vo.ListDataVO;
import cn.wnhyang.coolguard.decision.vo.create.ListDataCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.ListDataUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Mapper
public interface ListDataConvert {

    ListDataConvert INSTANCE = Mappers.getMapper(ListDataConvert.class);

    ListData convert(ListDataCreateVO createVO);

    ListData convert(ListDataUpdateVO updateVO);

    ListDataVO convert(ListData po);

    PageResult<ListDataVO> convert(PageResult<ListData> pageResult);

}
