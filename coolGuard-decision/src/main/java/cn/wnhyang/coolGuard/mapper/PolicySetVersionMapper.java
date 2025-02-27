package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
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

    default PolicySetVersion selectByCode(String code) {
        return selectOne(PolicySetVersion::getCode, code);
    }

    default PolicySetVersion selectLatestByAppNameAndCode(String appName, String code) {
        return selectOne(new LambdaQueryWrapperX<PolicySetVersion>()
                .eq(PolicySetVersion::getAppName, appName)
                .eq(PolicySetVersion::getCode, code)
                .eq(PolicySetVersion::getLatest, Boolean.TRUE));
    }

    default void deleteByCode(String code) {
        delete(PolicySetVersion::getCode, code);
    }
}
