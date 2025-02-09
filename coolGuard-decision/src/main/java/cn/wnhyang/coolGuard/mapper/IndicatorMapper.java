package cn.wnhyang.coolGuard.mapper;

import cn.hutool.core.util.StrUtil;
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
                .eqIfPresent(Indicator::getPublish, pageVO.getPublish())
                .isNotNull(StrUtil.isNotBlank(pageVO.getScene()), Indicator::getScenes)
                .ne(StrUtil.isNotBlank(pageVO.getScene()), Indicator::getScenes, "''")
                .apply(StrUtil.isNotBlank(pageVO.getScene()), "JSON_CONTAINS(scenes, '\"" + pageVO.getScene() + "\"')"));
    }

    default List<Long> selectIdListByScene(String sceneType, String scene) {
        return selectObjs(new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .isNotNull(Indicator::getScenes)
                .ne(Indicator::getScenes, "''")
                .apply("JSON_CONTAINS(scenes, '\"" + scene + "\"')").select(Indicator::getId));
    }

    default PageResult<Indicator> selectPageByScene(PageParam pageVO, String sceneType, String scene) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .isNotNull(Indicator::getScenes)
                .ne(Indicator::getScenes, "''")
                .apply("JSON_CONTAINS(scenes, '\"" + scene + "\"')"));
    }

    default List<Indicator> selectListByScene(String sceneType, String scene) {
        return selectList(new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getSceneType, sceneType)
                .isNotNull(Indicator::getScenes)
                .ne(Indicator::getScenes, "''")
                .apply("JSON_CONTAINS(scenes, '\"" + scene + "\"')"));
    }

    @Cacheable(value = RedisKey.INDICATORS + "::a-p", key = "#app+'-'+#policySet", unless = "#result == null")
    default List<Indicator> selectListByScenes(String app, String policySet) {
        return selectList(new LambdaQueryWrapperX<Indicator>()
                .and(w -> w.eq(Indicator::getSceneType, SceneType.APP)
                        .isNotNull(Indicator::getScenes)
                        .ne(Indicator::getScenes, "''")
                        .apply("JSON_CONTAINS(scenes, '\"" + app + "\"')"))
                .or(w -> w.eq(Indicator::getSceneType, SceneType.POLICY_SET)
                        .isNotNull(Indicator::getScenes)
                        .ne(Indicator::getScenes, "''")
                        .apply("JSON_CONTAINS(scenes, '\"" + policySet + "\"')"))
        );
    }

    default Indicator selectByName(String name) {
        return selectOne(Indicator::getName, name);
    }

    default void updateByCode(Indicator indicator) {
        update(indicator, new LambdaQueryWrapperX<Indicator>()
                .eq(Indicator::getCode, indicator.getCode()));
    }

    default Indicator selectByCode(String code) {
        return selectOne(Indicator::getCode, code);
    }
}
