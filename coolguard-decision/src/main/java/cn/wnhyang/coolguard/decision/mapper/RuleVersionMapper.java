package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.constant.RuleStatus;
import cn.wnhyang.coolguard.decision.entity.RuleVersion;
import cn.wnhyang.coolguard.decision.vo.page.RuleVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则版本表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface RuleVersionMapper extends BaseMapperX<RuleVersion> {

    default PageResult<RuleVersion> selectPage(RuleVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<RuleVersion>());
    }

    default PageResult<RuleVersion> selectPageByCode(RuleVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, pageVO.getCode())
                .orderByDesc(RuleVersion::getVersion));
    }

    default RuleVersion selectLatestByCode(String code) {
        return selectOne(RuleVersion::getCode, code, RuleVersion::getLatest, Boolean.TRUE);
    }

    default List<RuleVersion> selectLatestByPolicyCode(String policyCode) {
        return selectList(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getPolicyCode, policyCode)
                .eq(RuleVersion::getLatest, Boolean.TRUE));
    }

    default List<RuleVersion> selectLatestRunningByPolicyCode(String policyCode) {
        return selectList(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getPolicyCode, policyCode)
                .eq(RuleVersion::getLatest, Boolean.TRUE)
                .ne(RuleVersion::getStatus, RuleStatus.OFF));
    }

    default List<RuleVersion> selectLatestList() {
        return selectList(RuleVersion::getLatest, Boolean.TRUE);
    }

    default List<RuleVersion> selectByCode(String code) {
        return selectList(RuleVersion::getCode, code);
    }

    default RuleVersion selectLatestVersion(String code) {
        return selectOne(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, code)
                .orderByDesc(RuleVersion::getVersion)
                .last("LIMIT 1"));
    }

    default RuleVersion selectByCv(CvQueryVO queryVO) {
        return selectOne(RuleVersion::getCode, queryVO.getCode(), RuleVersion::getVersion, queryVO.getVersion());
    }
}
