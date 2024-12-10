package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.ListSet;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.ListSetVO;
import cn.wnhyang.coolGuard.vo.create.ListSetCreateVO;
import cn.wnhyang.coolGuard.vo.update.ListSetUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Mapper
public interface ListSetConvert {

    ListSetConvert INSTANCE = Mappers.getMapper(ListSetConvert.class);

    ListSet convert(ListSetCreateVO createVO);

    ListSet convert(ListSetUpdateVO updateVO);

    ListSetVO convert(ListSet po);

    PageResult<ListSetVO> convert(PageResult<ListSet> pageResult);

}
