package cn.wnhyang.coolguard.decision.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.dto.PolicyDTO;
import cn.wnhyang.coolguard.decision.dto.PolicySetDTO;
import cn.wnhyang.coolguard.decision.entity.Policy;
import cn.wnhyang.coolguard.decision.entity.PolicySet;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 策略集表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicySetMapper extends BaseMapperX<PolicySet> {

    default PageResult<PolicySetDTO> selectPage(PolicySetPageVO pageVO) {
        return selectJoinPage(pageVO, PolicySetDTO.class, new MPJLambdaWrapper<PolicySet>()
                .setAlias("t")
                .selectAll(PolicySet.class)
                .selectAs("t1", PolicySetVersion::getLatest, PolicyDTO::getLatest)
                .selectAs("t1", PolicySetVersion::getVersion, PolicySetDTO::getVersion)
                .selectAs("t1", PolicySetVersion::getVersionDesc, PolicySetDTO::getVersionDesc)
                .leftJoin(PolicySetVersion.class, t2 -> {
                    t2.setAlias("t2").select(PolicySetVersion::getCode).select(PolicySetVersion::getLatest).select(PolicySetVersion::getVersion).select(PolicySetVersion::getVersionDesc)
                            .innerJoin("""
                                    (SELECT code, MAX(version) AS max_version FROM de_policy_set_version GROUP BY code) t3 ON t2.code = t3.code AND t2.version = t3.max_version"""
                            );
                }, PolicySetVersion::getCode, Policy::getCode)
                .eqIfExists(PolicySet::getAppCode, pageVO.getAppCode())
                .likeIfExists(PolicySet::getName, pageVO.getName())
                .likeIfExists(PolicySet::getCode, pageVO.getCode())
                // 如果有latest，则查询最新版本
                .eq(ObjUtil.isNotNull(pageVO.getLatest()) && pageVO.getLatest(), PolicySetVersion::getLatest, pageVO.getLatest())
                // 如果有hasVersion，则查询有版本
                .isNotNull(ObjUtil.isNotNull(pageVO.getHasVersion()) && pageVO.getHasVersion(), PolicySetVersion::getVersion)
        );
    }

    default PolicySet selectByCode(String code) {
        return selectOne(PolicySet::getCode, code);
    }

    default PolicySet selectByAppAndCode(String appCode, String code) {
        return selectOne(PolicySet::getAppCode, appCode, PolicySet::getCode, code);
    }

    default List<PolicySet> selectList(Set<String> setCodes, String appCode, String name, String code) {
        return selectList(new LambdaQueryWrapperX<PolicySet>()
                .inIfPresent(PolicySet::getCode, setCodes)
                .eqIfPresent(PolicySet::getAppCode, appCode)
                .likeIfPresent(PolicySet::getName, name)
                .eqIfPresent(PolicySet::getCode, code));
    }

    default PolicySet selectByName(String name) {
        return selectOne(PolicySet::getName, name);
    }

    default void updateByCode(PolicySet policySet) {
        update(policySet, new LambdaQueryWrapperX<PolicySet>()
                .eq(PolicySet::getCode, policySet.getCode()));
    }
}
