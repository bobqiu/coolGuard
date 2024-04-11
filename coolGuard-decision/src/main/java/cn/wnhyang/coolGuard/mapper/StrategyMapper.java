package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Strategy;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.StrategyPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 策略表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface StrategyMapper extends BaseMapperX<Strategy> {

    default PageResult<Strategy> selectPage(StrategyPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Strategy>());
    }

    default List<Strategy> selectListBySetId(Long setId) {
        return selectList(Strategy::getStrategySetId, setId);
    }

    @Cacheable(cacheNames = "strategyByCode", key = "#code", unless = "#result == null")
    default Strategy selectByCode(String code) {
        return selectOne(Strategy::getCode, code);
    }
}
