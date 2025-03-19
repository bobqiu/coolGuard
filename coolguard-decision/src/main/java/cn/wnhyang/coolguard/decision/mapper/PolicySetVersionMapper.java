package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 策略集表版本 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Mapper
public interface PolicySetVersionMapper extends BaseMapperX<PolicySetVersion> {

    default PageResult<PolicySetVersion> selectPage(PolicySetVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicySetVersion>());
    }

    default PageResult<PolicySetVersion> selectPageByCode(PolicySetVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicySetVersion>()
                .eq(PolicySetVersion::getCode, pageVO.getCode())
                .orderByDesc(PolicySetVersion::getVersion));
    }

    default PolicySetVersion selectLatest(String code) {
        return selectOne(PolicySetVersion::getCode, code, PolicySetVersion::getLatest, Boolean.TRUE);
    }

    default List<PolicySetVersion> selectLatestList() {
        return selectList(PolicySetVersion::getLatest, Boolean.TRUE);
    }

    default PolicySetVersion selectLatestVersion(String code) {
        return selectOne(new LambdaQueryWrapperX<PolicySetVersion>()
                .eq(PolicySetVersion::getCode, code)
                .orderByDesc(PolicySetVersion::getVersion)
                .last("LIMIT 1"));
    }

    default void deleteBySetCode(String code) {
        delete(PolicySetVersion::getCode, code);
    }

    default List<PolicySetVersion> selectByCode(String code) {
        return selectList(PolicySetVersion::getCode, code);
    }

    default PolicySetVersion selectLatestByAppAndCode(String appCode, String code) {
        return selectOne(new LambdaQueryWrapperX<PolicySetVersion>()
                .eq(PolicySetVersion::getAppCode, appCode)
                .eq(PolicySetVersion::getCode, code)
                .eq(PolicySetVersion::getLatest, Boolean.TRUE));
    }

    default PolicySetVersion selectByCv(CvQueryVO queryVO) {
        return selectOne(PolicySetVersion::getCode, queryVO.getCode(), PolicySetVersion::getVersion, queryVO.getVersion());
    }
}
