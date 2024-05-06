package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 规则表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface RuleMapper extends BaseMapperX<Rule> {

    default PageResult<Rule> selectPage(RulePageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Rule>()
                .likeIfPresent(Rule::getName, pageVO.getName())
                .likeIfPresent(Rule::getCode, pageVO.getCode())
                .eqIfPresent(Rule::getDisposalId, pageVO.getDisposalId()));
    }

    @Cacheable(cacheNames = RedisKey.RULE + "::co", key = "#code", unless = "#result == null")
    default Rule selectByCode(String code) {
        return selectOne(Rule::getCode, code);
    }

    @Cacheable(cacheNames = RedisKey.RULES + "::sId", key = "#strategyId", unless = "#result == null")
    default List<Rule> selectByStrategyId(Long strategyId) {
        return selectList(new LambdaQueryWrapperX<Rule>().eq(Rule::getStrategyId, strategyId).orderByDesc(Rule::getSort));
    }

    @Cacheable(cacheNames = RedisKey.RULES + "::sId", key = "#strategyId+'-'+#status", unless = "#result == null")
    default List<Rule> selectByStrategyIdAndStatus(Long strategyId, String status) {
        return selectList(new LambdaQueryWrapperX<Rule>().eq(Rule::getStrategyId, strategyId).eq(Rule::getStatus, status).orderByDesc(Rule::getSort));
    }

    default Set<Long> selectStrategyId(String name, String code) {
        return selectList(new LambdaQueryWrapperX<Rule>()
                .likeIfPresent(Rule::getName, name)
                .eqIfPresent(Rule::getCode, code)
                .select(Rule::getStrategyId))
                .stream().map(Rule::getStrategyId).collect(Collectors.toSet());
    }
}
