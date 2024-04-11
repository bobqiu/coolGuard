package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.StrategySet;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.StrategySetVO;
import cn.wnhyang.coolGuard.vo.create.StrategySetCreateVO;
import cn.wnhyang.coolGuard.vo.update.StrategySetUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface StrategySetConvert {

    StrategySetConvert INSTANCE = Mappers.getMapper(StrategySetConvert.class);

    StrategySet convert(StrategySetCreateVO createVO);

    StrategySet convert(StrategySetUpdateVO updateVO);

    StrategySetVO convert(StrategySet po);

    PageResult<StrategySetVO> convert(PageResult<StrategySet> pageResult);

}
