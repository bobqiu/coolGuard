package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Application;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ApplicationPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

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
                .likeIfPresent(Application::getDisplayName, pageVO.getDisplayName())
                .likeIfPresent(Application::getName, pageVO.getName()));
    }

    @Cacheable(cacheNames = "applicationByName", key = "#name", unless = "#result == null")
    default Application selectByName(String name) {
        return selectOne(Application::getName, name);
    }
}
