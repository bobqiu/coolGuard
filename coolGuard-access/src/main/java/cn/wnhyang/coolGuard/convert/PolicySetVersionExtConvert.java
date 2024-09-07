package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.PolicySetVersionExt;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVersionExtVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionExtCreateVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionExtUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略集版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicySetVersionExtConvert {

    PolicySetVersionExtConvert INSTANCE = Mappers.getMapper(PolicySetVersionExtConvert.class);

    PolicySetVersionExt convert(PolicySetVersionExtCreateVO createVO);

    PolicySetVersionExt convert(PolicySetVersionExtUpdateVO updateVO);

    PolicySetVersionExtVO convert(PolicySetVersionExt po);

    PageResult<PolicySetVersionExtVO> convert(PageResult<PolicySetVersionExt> pageResult);

}
