package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.ListData;
import cn.wnhyang.coolguard.decision.vo.page.ListDataPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 名单数据表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Mapper
public interface ListDataMapper extends BaseMapperX<ListData> {

    default PageResult<ListData> selectPage(ListDataPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<ListData>()
                .eqIfPresent(ListData::getListSetCode, pageVO.getListSetCode())
                .eqIfPresent(ListData::getSource, pageVO.getSource())
                .likeIfPresent(ListData::getValue, pageVO.getValue()));
    }

    default List<String> selectListBySetCode(String setCode) {
        return selectObjs(new LambdaQueryWrapperX<ListData>()
                .eq(ListData::getListSetCode, setCode).select(ListData::getValue));
    }

    default Long selectCountBySetCode(String code) {
        return selectCount(ListData::getListSetCode, code);
    }
}
