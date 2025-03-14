package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.RuleScript;
import cn.wnhyang.coolguard.decision.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规则脚本表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface RuleScriptMapper extends BaseMapperX<RuleScript> {

    default PageResult<RuleScript> selectPage(RuleScriptPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<RuleScript>());
    }
}
