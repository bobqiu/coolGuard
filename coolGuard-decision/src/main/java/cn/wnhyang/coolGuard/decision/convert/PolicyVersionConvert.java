package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.entity.Policy;
import cn.wnhyang.coolGuard.decision.entity.PolicyVersion;
import cn.wnhyang.coolGuard.decision.vo.PolicyVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Mapper
public interface PolicyVersionConvert {

    PolicyVersionConvert INSTANCE = Mappers.getMapper(PolicyVersionConvert.class);

    PolicyVersionVO convert(PolicyVersion po);

    PageResult<PolicyVersionVO> convert(PageResult<PolicyVersion> pageResult);

    @Mapping(target = "id", ignore = true)
    PolicyVersion convert(Policy policy);
}
