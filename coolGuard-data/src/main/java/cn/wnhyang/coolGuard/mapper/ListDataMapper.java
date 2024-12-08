package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.ListData;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ListDataPageVO;
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
                .eqIfPresent(ListData::getValue, pageVO.getValue()));
    }

    default List<String> selectListBySetCode(String setCode) {
        return selectObjs(new LambdaQueryWrapperX<ListData>()
                .eq(ListData::getListSetCode, setCode).select(ListData::getValue));
    }
}
