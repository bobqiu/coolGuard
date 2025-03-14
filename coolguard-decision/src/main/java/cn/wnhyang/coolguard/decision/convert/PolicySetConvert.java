package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.context.PolicyContext;
import cn.wnhyang.coolguard.decision.dto.PolicySetDTO;
import cn.wnhyang.coolguard.decision.entity.PolicySet;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.entity.Rule;
import cn.wnhyang.coolguard.decision.entity.RuleVersion;
import cn.wnhyang.coolguard.decision.vo.PolicySetVO;
import cn.wnhyang.coolguard.decision.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.PolicySetUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicySetConvert {

    PolicySetConvert INSTANCE = Mappers.getMapper(PolicySetConvert.class);

    PolicySet convert(PolicySetCreateVO createVO);

    PolicySet convert(PolicySetUpdateVO updateVO);

    PolicySetVO convert(PolicySet po);

    PageResult<PolicySetVO> convert(PageResult<PolicySet> pageResult);

    List<PolicySetVO> convert(List<PolicySet> list);

    PolicySet convert(PolicySetVersion policySetVersion);

    Rule convert(RuleVersion ruleVersion);

    PageResult<PolicySetVO> convert2(PageResult<PolicySetDTO> policySetPageResult);

    PolicyContext.PolicySetCtx convert2Ctx(PolicySetVersion policySetVersion);
}
