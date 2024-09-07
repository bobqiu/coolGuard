package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVersionVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicySetVersionConvert {

    PolicySetVersionConvert INSTANCE = Mappers.getMapper(PolicySetVersionConvert.class);

    PolicySetVersion convert(PolicySetVersionCreateVO createVO);

    PolicySetVersion convert(PolicySetVersionUpdateVO updateVO);

    PolicySetVersionVO convert(PolicySetVersion po);

    PageResult<PolicySetVersionVO> convert(PageResult<PolicySetVersion> pageResult);

}
