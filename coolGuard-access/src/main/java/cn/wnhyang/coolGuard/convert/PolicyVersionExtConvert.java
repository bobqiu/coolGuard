package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.PolicyVersionExt;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicyVersionExtVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicyVersionExtConvert {

    PolicyVersionExtConvert INSTANCE = Mappers.getMapper(PolicyVersionExtConvert.class);

    PolicyVersionExtVO convert(PolicyVersionExt po);

    PageResult<PolicyVersionExtVO> convert(PageResult<PolicyVersionExt> pageResult);

}
