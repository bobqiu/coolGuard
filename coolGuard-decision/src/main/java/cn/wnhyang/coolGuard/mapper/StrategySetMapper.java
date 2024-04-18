package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.StrategySet;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.StrategySetPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

/**
 * 策略集表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface StrategySetMapper extends BaseMapperX<StrategySet> {

    default PageResult<StrategySet> selectPage(StrategySetPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<StrategySet>());
    }

    @Cacheable(cacheNames = "strategySetByCode", key = "#{code}", unless = "#result == null")
    default StrategySet selectByCode(String code) {
        return selectOne(StrategySet::getCode, code);
    }

    @Cacheable(cacheNames = "strategySetByAppNameAndCode", key = "#appName+'-'+#code", unless = "#result == null")
    default StrategySet selectByAppNameAndCode(String appName, String code) {
        return selectOne(StrategySet::getAppName, appName, StrategySet::getCode, code);
    }

    default List<StrategySet> selectList(Set<Long> ids, String appName, String name, String code) {
        return selectList(new LambdaQueryWrapperX<StrategySet>()
                .inIfPresent(StrategySet::getId, ids)
                .eqIfPresent(StrategySet::getAppName, appName)
                .likeIfPresent(StrategySet::getName, name)
                .eqIfPresent(StrategySet::getCode, code));
    }
}
