package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Application;
import cn.wnhyang.coolguard.decision.vo.page.ApplicationPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface ApplicationMapper extends BaseMapperX<Application> {

    default PageResult<Application> selectPage(ApplicationPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Application>()
                .likeIfPresent(Application::getName, pageVO.getName())
                .likeIfPresent(Application::getCode, pageVO.getCode()));
    }

    default Application selectByCode(String code) {
        return selectOne(Application::getCode, code);
    }

    default Application selectByName(String name) {
        return selectOne(Application::getName, name);
    }
}
