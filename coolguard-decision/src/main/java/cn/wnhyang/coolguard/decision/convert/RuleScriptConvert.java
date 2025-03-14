package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.RuleScript;
import cn.wnhyang.coolguard.decision.vo.RuleScriptVO;
import cn.wnhyang.coolguard.decision.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.RuleScriptUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface RuleScriptConvert {

    RuleScriptConvert INSTANCE = Mappers.getMapper(RuleScriptConvert.class);

    RuleScript convert(RuleScriptCreateVO createVO);

    RuleScript convert(RuleScriptUpdateVO updateVO);

    RuleScriptVO convert(RuleScript po);

    PageResult<RuleScriptVO> convert(PageResult<RuleScript> pageResult);

}
