package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.dto.RuleDTO;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface RuleConvert {

    RuleConvert INSTANCE = Mappers.getMapper(RuleConvert.class);

    Rule convert(RuleCreateVO createVO);

    Rule convert(RuleUpdateVO updateVO);

    RuleVO convert(Rule po);

    PageResult<RuleVO> convert(PageResult<Rule> pageResult);

    List<RuleVO> convert(List<Rule> list);

    List<PolicyContext.RuleCtx> convert2Ctx(List<Rule> rules);

    PageResult<RuleVO> convert2(PageResult<RuleDTO> rulePageResult);
}
