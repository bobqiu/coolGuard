package cn.wnhyang.coolguard.decision.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.dto.PolicyDTO;
import cn.wnhyang.coolguard.decision.entity.Policy;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.vo.page.PolicyPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 策略表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicyMapper extends BaseMapperX<Policy> {

    default PageResult<PolicyDTO> selectPage(PolicyPageVO pageVO) {
        return selectJoinPage(pageVO, PolicyDTO.class, new MPJLambdaWrapper<Policy>()
                .setAlias("t")
                .selectAll(Policy.class)
                .selectAs("t1", PolicyVersion::getLatest, PolicyDTO::getLatest)
                .selectAs("t1", PolicyVersion::getVersion, PolicyDTO::getVersion)
                .selectAs("t1", PolicyVersion::getVersionDesc, PolicyDTO::getVersionDesc)
                .leftJoin(PolicyVersion.class, t2 -> {
                    t2.setAlias("t2").select(PolicyVersion::getCode).select(PolicyVersion::getLatest).select(PolicyVersion::getVersion).select(PolicyVersion::getVersionDesc)
                            .innerJoin("""
                                    (SELECT code, MAX(version) AS max_version FROM de_policy_version GROUP BY code) t3 ON t2.code = t3.code AND t2.version = t3.max_version"""
                            );
                }, PolicyVersion::getCode, Policy::getCode)
                .eqIfExists(Policy::getPolicySetCode, pageVO.getPolicySetCode())
                .likeIfExists(Policy::getName, pageVO.getName())
                .likeIfExists(Policy::getCode, pageVO.getCode())
                .eqIfExists(Policy::getMode, pageVO.getMode())
                // 如果有latest，则查询最新版本
                .eq(ObjUtil.isNotNull(pageVO.getLatest()) && pageVO.getLatest(), PolicyVersion::getLatest, pageVO.getLatest())
                // 如果有hasVersion，则查询有版本
                .isNotNull(ObjUtil.isNotNull(pageVO.getHasVersion()) && pageVO.getHasVersion(), PolicyVersion::getVersion)
        );


    }

    default List<Policy> selectListBySetCode(String setCode) {
        return selectList(Policy::getPolicySetCode, setCode);
    }

    default Policy selectByCode(String code) {
        return selectOne(Policy::getCode, code);
    }

    default List<Policy> selectList(List<String> codes, String name, String code) {
        return selectList(new LambdaQueryWrapperX<Policy>()
                .inIfPresent(Policy::getCode, codes)
                .likeIfPresent(Policy::getName, name)
                .eqIfPresent(Policy::getCode, code));
    }

    default Policy selectByName(String name) {
        return selectOne(Policy::getName, name);
    }

    default void updateByCode(Policy policy) {
        update(policy, new LambdaQueryWrapperX<Policy>()
                .eq(Policy::getCode, policy.getCode()));
    }
}
