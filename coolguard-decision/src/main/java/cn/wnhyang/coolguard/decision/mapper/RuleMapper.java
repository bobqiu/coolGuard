package cn.wnhyang.coolguard.decision.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.constant.RuleStatus;
import cn.wnhyang.coolguard.decision.dto.RuleDTO;
import cn.wnhyang.coolguard.decision.entity.Rule;
import cn.wnhyang.coolguard.decision.entity.RuleVersion;
import cn.wnhyang.coolguard.decision.vo.page.RulePageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface RuleMapper extends BaseMapperX<Rule> {

    default PageResult<RuleDTO> selectPage(RulePageVO pageVO) {
        return selectJoinPage(pageVO, RuleDTO.class, new MPJLambdaWrapper<Rule>()
                .setAlias("t")
                .selectAll(Rule.class)
                .selectAs("t1", RuleVersion::getLatest, RuleDTO::getLatest)
                .selectAs("t1", RuleVersion::getVersion, RuleDTO::getVersion)
                .selectAs("t1", RuleVersion::getVersionDesc, RuleDTO::getVersionDesc)
                .leftJoin(RuleVersion.class, t2 -> {
                    t2.setAlias("t2").select(RuleVersion::getCode).select(RuleVersion::getLatest).select(RuleVersion::getVersion).select(RuleVersion::getVersionDesc)
                            .innerJoin("""
                                    (SELECT code, MAX(version) AS max_version FROM de_rule_version GROUP BY code) t3 ON t2.code = t3.code AND t2.version = t3.max_version"""
                            );
                }, RuleVersion::getCode, Rule::getCode)
                .eqIfExists(Rule::getPolicyCode, pageVO.getPolicyCode())
                .likeIfExists(Rule::getName, pageVO.getName())
                .likeIfExists(Rule::getCode, pageVO.getCode())
                .likeIfExists(Rule::getRuleId, pageVO.getRuleId())
                .eqIfExists(Rule::getDisposalCode, pageVO.getDisposalCode())
                .eqIfExists(Rule::getStatus, pageVO.getStatus())
                // 如果有latest，则查询最新版本
                .eq(ObjUtil.isNotNull(pageVO.getLatest()) && pageVO.getLatest(), RuleVersion::getLatest, pageVO.getLatest())
                // 如果有hasVersion，则查询有版本
                .isNotNull(ObjUtil.isNotNull(pageVO.getHasVersion()) && pageVO.getHasVersion(), RuleVersion::getVersion)
        );
    }

    default Rule selectByCode(String code) {
        return selectOne(Rule::getCode, code);
    }

    default List<Rule> selectListByPolicyCode(String policyCode) {
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
                .and(q -> q
                        .eq(Rule::getStatus, RuleStatus.ON)
                        .or()
                        .eq(Rule::getStatus, RuleStatus.MOCK)
                ));
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

    default void updateByCode(Rule rule) {
        update(rule, new LambdaQueryWrapperX<Rule>()
                .eq(Rule::getCode, rule.getCode()));
    }
}
