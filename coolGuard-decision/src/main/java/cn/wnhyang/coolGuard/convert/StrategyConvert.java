package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.Strategy;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.StrategyVO;
import cn.wnhyang.coolGuard.vo.create.StrategyCreateVO;
import cn.wnhyang.coolGuard.vo.update.StrategyUpdateVO;
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
public interface StrategyConvert {

    StrategyConvert INSTANCE = Mappers.getMapper(StrategyConvert.class);

    Strategy convert(StrategyCreateVO createVO);

    Strategy convert(StrategyUpdateVO updateVO);

    StrategyVO convert(Strategy po);

    PageResult<StrategyVO> convert(PageResult<Strategy> pageResult);

    List<StrategyVO> convert(List<Strategy> list);
}
