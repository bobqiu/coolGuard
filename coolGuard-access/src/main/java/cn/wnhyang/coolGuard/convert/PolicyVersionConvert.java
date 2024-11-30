package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.PolicyVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicyVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicyVersionConvert {

    PolicyVersionConvert INSTANCE = Mappers.getMapper(PolicyVersionConvert.class);

    PolicyVersionVO convert(PolicyVersion po);

    PageResult<PolicyVersionVO> convert(PageResult<PolicyVersion> pageResult);

}
