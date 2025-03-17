package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.vo.page.PolicyVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 策略版本表 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Mapper
public interface PolicyVersionMapper extends BaseMapperX<PolicyVersion> {

    default PageResult<PolicyVersion> selectPage(PolicyVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicyVersion>());
    }

    default PageResult<PolicyVersion> selectPageByCode(PolicyVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicyVersion>()
                .eq(PolicyVersion::getCode, pageVO.getCode())
                .orderByDesc(PolicyVersion::getVersion));
    }

    default PolicyVersion selectLatestByCode(String code) {
        return selectOne(PolicyVersion::getCode, code, PolicyVersion::getLatest, Boolean.TRUE);
    }

    default List<PolicyVersion> selectLatestBySetCode(String setCode) {
        return selectList(new LambdaQueryWrapperX<PolicyVersion>()
                .eq(PolicyVersion::getCode, setCode)
                .eq(PolicyVersion::getLatest, Boolean.TRUE));
    }

    default List<PolicyVersion> selectLatestList() {
        return selectList(PolicyVersion::getLatest, Boolean.TRUE);
    }

    default PolicyVersion selectLatestVersion(String code) {
        return selectOne(new LambdaQueryWrapperX<PolicyVersion>()
                .eq(PolicyVersion::getCode, code)
                .orderByDesc(PolicyVersion::getVersion)
                .last("LIMIT 1"));
    }

    default List<PolicyVersion> selectByCode(String code) {
        return selectList(PolicyVersion::getCode, code);
    }

    default PolicyVersion selectByCv(CvQueryVO queryVO) {
        return selectOne(PolicyVersion::getCode, queryVO.getCode(), PolicyVersion::getVersion, queryVO.getVersion());
    }
}
