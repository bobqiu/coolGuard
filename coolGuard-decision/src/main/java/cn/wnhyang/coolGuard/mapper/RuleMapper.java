package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 规则表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface RuleMapper extends BaseMapperX<Rule> {

    default PageResult<Rule> selectPage(RulePageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Rule>());
    }

    @Cacheable(cacheNames = "fieldByCode", key = "#code", unless = "#result == null")
    default Rule selectByCode(String code) {
        return selectOne(Rule::getCode, code);
    }

    @Cacheable(cacheNames = "ruleByStrategyId", key = "#strategyId", unless = "#result == null")
    default List<Rule> selectByStrategyId(Long strategyId) {
        return selectList(new LambdaQueryWrapperX<Rule>().eq(Rule::getStrategyId, strategyId).orderByDesc(Rule::getSort));
    }

    @Cacheable(cacheNames = "ruleByStrategyId", key = "#strategyId+'-'+#status", unless = "#result == null")
    default List<Rule> selectByStrategyIdAndStatus(Long strategyId, String status) {
        return selectList(new LambdaQueryWrapperX<Rule>().eq(Rule::getStrategyId, strategyId).eq(Rule::getStatus, status).orderByDesc(Rule::getSort));
    }
}
