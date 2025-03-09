package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.context.PolicyContext;
import cn.wnhyang.coolGuard.decision.dto.PolicyDTO;
import cn.wnhyang.coolGuard.decision.entity.Policy;
import cn.wnhyang.coolGuard.decision.entity.PolicyVersion;
import cn.wnhyang.coolGuard.decision.vo.PolicyVO;
import cn.wnhyang.coolGuard.decision.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.decision.vo.update.PolicyUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicyConvert {

    PolicyConvert INSTANCE = Mappers.getMapper(PolicyConvert.class);

    Policy convert(PolicyCreateVO createVO);

    Policy convert(PolicyUpdateVO updateVO);

    PolicyVO convert(Policy po);

    PageResult<PolicyVO> convert(PageResult<Policy> pageResult);

    List<PolicyVO> convert(List<Policy> list);

    Policy convert(PolicyVersion policyVersion);

    PageResult<PolicyVO> convert2(PageResult<PolicyDTO> policyPageResult);

    PolicyContext.PolicyCtx convert2Ctx(PolicyVersion policyVersion);
}
