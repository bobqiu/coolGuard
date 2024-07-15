package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Condition;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ConditionPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规则条件表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ConditionMapper extends BaseMapperX<Condition> {

    default PageResult<Condition> selectPage(ConditionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Condition>());
    }

}
