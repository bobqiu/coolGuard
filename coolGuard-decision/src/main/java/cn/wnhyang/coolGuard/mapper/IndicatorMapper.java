package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 指标表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface IndicatorMapper extends BaseMapperX<Indicator> {

    default PageResult<Indicator> selectPage(IndicatorPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Indicator>());
    }

    @Cacheable(cacheNames = "indicatorsByScene", key = "#scene+'-'+#sceneType", unless = "#result == null")
    default List<Indicator> selectListByScene(String scene, String sceneType) {
        return selectList(new LambdaQueryWrapperX<Indicator>().eq(Indicator::getScene, scene).eq(Indicator::getSceneType, sceneType));
    }
}
