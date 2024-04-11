package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.RuleCondition;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.RuleConditionVO;
import cn.wnhyang.coolGuard.vo.create.RuleConditionCreateVO;
import cn.wnhyang.coolGuard.vo.update.RuleConditionUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface RuleConditionConvert {

    RuleConditionConvert INSTANCE = Mappers.getMapper(RuleConditionConvert.class);

    RuleCondition convert(RuleConditionCreateVO createVO);

    RuleCondition convert(RuleConditionUpdateVO updateVO);

    RuleConditionVO convert(RuleCondition po);

    PageResult<RuleConditionVO> convert(PageResult<RuleCondition> pageResult);

}
