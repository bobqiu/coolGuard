package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Tag;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.TagPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Mapper
public interface TagMapper extends BaseMapperX<Tag> {

    default PageResult<Tag> selectPage(TagPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Tag>()
                .likeIfPresent(Tag::getName, pageVO.getName())
                .likeIfPresent(Tag::getCode, pageVO.getCode()));
    }

    default Tag selectByCode(String code) {
        return selectOne(Tag::getCode, code);
    }
}
