package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Tag;
import cn.wnhyang.coolguard.decision.vo.page.TagPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
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
