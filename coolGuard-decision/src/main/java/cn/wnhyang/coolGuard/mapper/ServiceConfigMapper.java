package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

/**
 * 服务配置表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface ServiceConfigMapper extends BaseMapperX<ServiceConfig> {

    default PageResult<ServiceConfig> selectPage(ServiceConfigPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<ServiceConfig>()
                .likeIfPresent(ServiceConfig::getName, pageVO.getName())
                .likeIfPresent(ServiceConfig::getDisplayName, pageVO.getDisplayName()));
    }

    @Cacheable(cacheNames = "serviceConfigByName", key = "#name", unless = "#result == null")
    default ServiceConfig selectByName(String name) {
        return selectOne(ServiceConfig::getName, name);
    }
}
