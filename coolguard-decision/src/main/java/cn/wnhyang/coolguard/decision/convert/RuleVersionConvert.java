package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Rule;
import cn.wnhyang.coolguard.decision.entity.RuleVersion;
import cn.wnhyang.coolguard.decision.vo.RuleVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface RuleVersionConvert {

    RuleVersionConvert INSTANCE = Mappers.getMapper(RuleVersionConvert.class);

    RuleVersionVO convert(RuleVersion po);

    PageResult<RuleVersionVO> convert(PageResult<RuleVersion> pageResult);

    @Mapping(target = "id", ignore = true)
    RuleVersion convert(Rule rule);
}
