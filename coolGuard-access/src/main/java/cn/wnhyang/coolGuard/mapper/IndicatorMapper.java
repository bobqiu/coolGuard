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
                .eqIfPresent(Indicator::getScenes, pageVO.getScene()));
    }

    default List<Long> selectIdListByScene(String sceneType, String scene) {
        return selectObjs(new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .apply("FIND_IN_SET({0}, scenes)", scene).select(Indicator::getId));
    }

    default PageResult<Indicator> selectPageByScene(PageParam pageVO, String sceneType, String scene) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .apply("FIND_IN_SET({0}, scenes)", scene));
    }

    default List<Indicator> selectListByScene(String sceneType, String scene) {
        return selectList(new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .apply("FIND_IN_SET({0}, scenes)", scene));
    }

    @Cacheable(cacheNames = RedisKey.INDICATORS + "::a-p", key = "#app+'-'+#policySet", unless = "#result == null")
    default List<Indicator> selectListByScenes(String app, String policySet) {
        return selectList(new LambdaQueryWrapperX<Indicator>()
                .and(w -> w.eq(Indicator::getSceneType, SceneType.APP).apply("FIND_IN_SET({0}, scenes)", app))
                .or(w -> w.eq(Indicator::getSceneType, SceneType.POLICY_SET).apply("FIND_IN_SET({0}, scenes)", policySet))
        );
    }
}
