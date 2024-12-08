package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.Tag;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.TagVO;
import cn.wnhyang.coolGuard.vo.create.TagCreateVO;
import cn.wnhyang.coolGuard.vo.update.TagUpdateVO;
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
