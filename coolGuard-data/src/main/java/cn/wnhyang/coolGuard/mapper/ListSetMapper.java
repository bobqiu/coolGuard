package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.ListSet;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ListSetPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 名单集表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Mapper
public interface ListSetMapper extends BaseMapperX<ListSet> {

    default PageResult<ListSet> selectPage(ListSetPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<ListSet>()
                .likeIfPresent(ListSet::getName, pageVO.getName()));
    }
}
