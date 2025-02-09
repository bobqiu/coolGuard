package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import org.apache.ibatis.annotations.Mapper;

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

    default PolicySetVersion selectLatest(String code) {
        return selectOne(PolicySetVersion::getCode, code, PolicySetVersion::getLatest, Boolean.TRUE);
    }

    default void deleteBySetCode(String code) {
        delete(PolicySetVersion::getCode, code);
    }

    default PolicySetVersion selectByCode(String code) {
        return selectOne(PolicySetVersion::getCode, code);
    }
}
