package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.entity.Application;
import cn.wnhyang.coolGuard.decision.vo.ApplicationVO;
import cn.wnhyang.coolGuard.decision.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolGuard.decision.vo.update.ApplicationUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface ApplicationConvert {

    ApplicationConvert INSTANCE = Mappers.getMapper(ApplicationConvert.class);

    Application convert(ApplicationCreateVO createVO);

    Application convert(ApplicationUpdateVO updateVO);

    ApplicationVO convert(Application po);

    PageResult<ApplicationVO> convert(PageResult<Application> pageResult);

}
