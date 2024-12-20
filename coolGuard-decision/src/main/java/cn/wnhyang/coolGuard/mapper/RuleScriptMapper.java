package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.RuleScript;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RuleScriptPageVO;
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
