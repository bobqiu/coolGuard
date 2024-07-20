package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageParam;
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
        return selectPage(pageVO, new LambdaQueryWrapperX<Indicator>()
                .likeIfPresent(Indicator::getName, pageVO.getName())
                .eqIfPresent(Indicator::getType, pageVO.getType())
                .eqIfPresent(Indicator::getSceneType, pageVO.getSceneType())
                .eqIfPresent(Indicator::getScene, pageVO.getScene()));
    }

    @Cacheable(cacheNames = RedisKey.INDICATOR + "::s-st", key = "#scene+'-'+#sceneType", unless = "#result == null")
    default List<Indicator> selectListByScene(String scene, String sceneType) {
        return selectList(new LambdaQueryWrapperX<Indicator>().eq(Indicator::getScene, scene)
                .eq(Indicator::getSceneType, sceneType));
    }

    default List<Long> selectIdListByScene(String scene, String sceneType) {
        return selectObjs(new LambdaQueryWrapperX<Indicator>().eq(Indicator::getScene, scene)
                .eq(Indicator::getSceneType, sceneType).select(Indicator::getId));
    }

    default PageResult<Indicator> selectPage(PageParam pageVO, String appName, String code) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Indicator>()
                .and(q -> q.eq(Indicator::getSceneType, SceneType.APP).eq(Indicator::getScene, appName))
                .or(q -> q.eq(Indicator::getSceneType, SceneType.POLICY_SET).eq(Indicator::getScene, code)));
    }

    default List<Indicator> selectList(String appName, String code) {
        return selectList(new LambdaQueryWrapperX<Indicator>()
                .and(q -> q.eq(Indicator::getSceneType, SceneType.APP).eq(Indicator::getScene, appName))
                .or(q -> q.eq(Indicator::getSceneType, SceneType.POLICY_SET).eq(Indicator::getScene, code)));
    }
}
