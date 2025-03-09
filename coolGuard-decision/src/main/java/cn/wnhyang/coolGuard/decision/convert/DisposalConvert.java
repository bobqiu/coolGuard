package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.context.PolicyContext;
import cn.wnhyang.coolGuard.decision.entity.Disposal;
import cn.wnhyang.coolGuard.decision.vo.DisposalVO;
import cn.wnhyang.coolGuard.decision.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.decision.vo.update.DisposalUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface DisposalConvert {

    DisposalConvert INSTANCE = Mappers.getMapper(DisposalConvert.class);

    Disposal convert(DisposalCreateVO createVO);

    Disposal convert(DisposalUpdateVO updateVO);

    DisposalVO convert(Disposal po);

    PageResult<DisposalVO> convert(PageResult<Disposal> pageResult);

    List<PolicyContext.DisposalCtx> convert2Ctx(List<Disposal> disposalList);

    List<DisposalVO> convert(List<Disposal> disposalList);
}
