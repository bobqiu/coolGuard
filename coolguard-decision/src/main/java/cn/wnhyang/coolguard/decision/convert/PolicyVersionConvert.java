package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Policy;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.vo.PolicyVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    List<PolicyVersionVO> convert(List<PolicyVersion> list);
}
