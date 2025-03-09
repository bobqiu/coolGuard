package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.entity.PolicySet;
import cn.wnhyang.coolGuard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.decision.vo.PolicySetVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "id", ignore = true)
    PolicySetVersion convert(PolicySet policySet);

}
