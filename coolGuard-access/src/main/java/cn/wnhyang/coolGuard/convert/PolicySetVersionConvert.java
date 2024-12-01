package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略集表版本
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Mapper
public interface PolicySetVersionConvert {

    PolicySetVersionConvert INSTANCE = Mappers.getMapper(PolicySetVersionConvert.class);

    PolicySetVersionVO convert(PolicySetVersion po);

    PageResult<PolicySetVersionVO> convert(PageResult<PolicySetVersion> pageResult);

}
