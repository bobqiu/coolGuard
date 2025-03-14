package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Tag;
import cn.wnhyang.coolguard.decision.vo.TagVO;
import cn.wnhyang.coolguard.decision.vo.create.TagCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.TagUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 标签表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    Tag convert(TagCreateVO createVO);

    Tag convert(TagUpdateVO updateVO);

    TagVO convert(Tag po);

    PageResult<TagVO> convert(PageResult<Tag> pageResult);

}
