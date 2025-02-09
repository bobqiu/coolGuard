package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.RuleStatus;
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
        return selectPage(pageVO, new LambdaQueryWrapperX<Rule>()
                .eqIfPresent(Rule::getPolicyCode, pageVO.getPolicyCode())
                .likeIfPresent(Rule::getName, pageVO.getName())
                .likeIfPresent(Rule::getCode, pageVO.getCode())
                .likeIfPresent(Rule::getRuleId, pageVO.getRuleId())
                .eqIfPresent(Rule::getDisposalCode, pageVO.getDisposalCode()));
    }

    @Cacheable(value = RedisKey.RULE + "::co", key = "#code", unless = "#result == null")
    default Rule selectByCode(String code) {
        return selectOne(Rule::getCode, code);
    }

    @Cacheable(value = RedisKey.RULES + "::sId", key = "#policyCode", unless = "#result == null")
    default List<Rule> selectByPolicyCode(String policyCode) {
        return selectList(new LambdaQueryWrapperX<Rule>().eq(Rule::getPolicyCode, policyCode).orderByDesc(Rule::getSort));
    }

    default List<String> selectPolicyCodeList(String name, String code) {
        return selectObjs(new LambdaQueryWrapperX<Rule>()
                .likeIfPresent(Rule::getName, name)
                .eqIfPresent(Rule::getCode, code)
                .select(Rule::getPolicyCode));
    }

    default List<Rule> selectByDisposalCode(String disposalCode) {
        return selectList(Rule::getDisposalCode, disposalCode);
    }

    default List<Rule> selectRunningListByPolicyCode(String policyCode) {
        return selectList(new LambdaQueryWrapperX<Rule>()
                .eqIfPresent(Rule::getPolicyCode, policyCode)
                .or(q -> q.eq(Rule::getStatus, RuleStatus.ON).eq(Rule::getStatus, RuleStatus.MOCK)));
    }

    default List<Rule> selectListByPolicyCodeAndStatus(String policyCode, String status) {
        return selectList(new LambdaQueryWrapperX<Rule>()
                .eqIfPresent(Rule::getPolicyCode, policyCode)
                .eqIfPresent(Rule::getStatus, status));
    }

    default Rule selectByRuleId(String ruleId) {
        return selectOne(Rule::getRuleId, ruleId);
    }

    default Rule selectByName(String name) {
        return selectOne(Rule::getName, name);
    }
}
