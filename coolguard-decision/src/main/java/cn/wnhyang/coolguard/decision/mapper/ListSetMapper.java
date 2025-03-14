package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.ListSet;
import cn.wnhyang.coolguard.decision.vo.page.ListSetPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
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
                .likeIfPresent(ListSet::getCode, pageVO.getCode())
                .likeIfPresent(ListSet::getName, pageVO.getName()));
    }

    default ListSet selectByCode(String setCode) {
        return selectOne(ListSet::getCode, setCode);
    }
}
