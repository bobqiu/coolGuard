package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
import org.apache.ibatis.annotations.Mapper;

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

    default RuleVersion selectLatest(String code) {
        return selectOne(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, code)
                .eq(RuleVersion::getLatest, Boolean.TRUE));
    }

    default void deleteBySetCode(String code) {
        delete(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, code));
    }

    default RuleVersion selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<RuleVersion>()
                .eq(RuleVersion::getCode, code));
    }
}
