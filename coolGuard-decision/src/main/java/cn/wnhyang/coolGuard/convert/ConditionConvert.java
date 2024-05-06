package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.Condition;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.ConditionVO;
import cn.wnhyang.coolGuard.vo.create.ConditionCreateVO;
import cn.wnhyang.coolGuard.vo.update.ConditionUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ConditionConvert {

    ConditionConvert INSTANCE = Mappers.getMapper(ConditionConvert.class);

    Condition convert(ConditionCreateVO createVO);

    Condition convert(ConditionUpdateVO updateVO);

    ConditionVO convert(Condition po);

    PageResult<ConditionVO> convert(PageResult<Condition> pageResult);

}
