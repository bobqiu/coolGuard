package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.vo.update.PolicyUpdateVO;
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
}
