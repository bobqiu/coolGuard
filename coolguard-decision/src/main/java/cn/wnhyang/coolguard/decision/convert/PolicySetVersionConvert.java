package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.PolicySet;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.vo.PolicySetVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    List<PolicySetVersionVO> convert(List<PolicySetVersion> list);
}
