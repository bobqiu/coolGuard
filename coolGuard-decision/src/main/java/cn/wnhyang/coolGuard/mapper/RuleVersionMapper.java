package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
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

    default List<RuleVersion> selectLatestList() {
        return selectList(RuleVersion::getLatest, Boolean.TRUE);
    }

    default RuleVersion selectByCode(String code) {
        return selectOne(RuleVersion::getCode, code);
    }

    default RuleVersion selectLatestVersion(String code) {
        return selectOne(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, code)
                .orderByDesc(RuleVersion::getVersion)
                .last("LIMIT 1"));
    }

    default void deleteByCode(String code) {
        delete(RuleVersion::getCode, code);
    }
}
