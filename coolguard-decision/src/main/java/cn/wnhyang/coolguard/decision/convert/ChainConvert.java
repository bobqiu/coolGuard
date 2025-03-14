package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Chain;
import cn.wnhyang.coolguard.decision.vo.ChainVO;
import cn.wnhyang.coolguard.decision.vo.create.ChainCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.ChainUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ChainConvert {

    ChainConvert INSTANCE = Mappers.getMapper(ChainConvert.class);

    Chain convert(ChainCreateVO createVO);

    Chain convert(ChainUpdateVO updateVO);

    ChainVO convert(Chain po);

    PageResult<ChainVO> convert(PageResult<Chain> pageResult);

}
