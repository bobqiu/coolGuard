package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.DisposalVO;
import cn.wnhyang.coolGuard.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.vo.update.DisposalUpdateVO;
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
}
