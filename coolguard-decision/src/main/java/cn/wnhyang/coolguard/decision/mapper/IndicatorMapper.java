package cn.wnhyang.coolguard.decision.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.pojo.PageParam;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.constant.SceneType;
import cn.wnhyang.coolguard.decision.dto.IndicatorDTO;
import cn.wnhyang.coolguard.decision.entity.Indicator;
import cn.wnhyang.coolguard.decision.entity.IndicatorVersion;
import cn.wnhyang.coolguard.decision.vo.page.IndicatorPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 指标表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface IndicatorMapper extends BaseMapperX<Indicator> {


    default PageResult<IndicatorDTO> selectPage(IndicatorPageVO pageVO) {
        return selectJoinPage(pageVO, IndicatorDTO.class, new MPJLambdaWrapper<Indicator>()
                        .setAlias("t")
                        .selectAll(Indicator.class)
                        .selectAs("t1", IndicatorVersion::getLatest, IndicatorDTO::getLatest)
                        .selectAs("t1", IndicatorVersion::getVersion, IndicatorDTO::getVersion)
                        .selectAs("t1", IndicatorVersion::getVersionDesc, IndicatorDTO::getVersionDesc)
                        .leftJoin(IndicatorVersion.class, t2 -> {
                            t2.setAlias("t2").select(IndicatorVersion::getCode).select(IndicatorVersion::getLatest).select(IndicatorVersion::getVersion).select(IndicatorVersion::getVersionDesc)
                                    .innerJoin("""
                                            (SELECT code, MAX(version) AS max_version FROM de_indicator_version GROUP BY code) t3 ON t2.code = t3.code AND t2.version = t3.max_version"""
                                    );
                        }, IndicatorVersion::getCode, Indicator::getCode)
//                        .leftJoin("(SELECT t2.code, t2.latest, t2.version, t2.version_desc\n" +
//                                "                    FROM de_indicator_version t2\n" +
//                                "                             JOIN (SELECT code, MAX(version) AS max_version\n" +
//                                "                                   FROM de_indicator_version\n" +
//                                "                                   GROUP BY code) t3 ON t2.code = t3.code AND t2.version = t3.max_version) t1\n" +
//                                "                   ON (t1.code = t.code)")
                        .likeIfExists(Indicator::getName, pageVO.getName())
                        .eqIfExists(Indicator::getType, pageVO.getType())
                        .eqIfExists(Indicator::getSceneType, pageVO.getSceneType())
                        .eqIfExists(Indicator::getPublish, pageVO.getPublish())
                        // 如果有latest，则查询最新版本
                        .eq(ObjUtil.isNotNull(pageVO.getLatest()) && pageVO.getLatest(), IndicatorVersion::getLatest, pageVO.getLatest())
                        // 如果有hasVersion，则查询有版本
                        .isNotNull(ObjUtil.isNotNull(pageVO.getHasVersion()) && pageVO.getHasVersion(), IndicatorVersion::getVersion)
                        // 筛选场景
                        .isNotNull(StrUtil.isNotBlank(pageVO.getScene()), Indicator::getScenes)
                        .ne(StrUtil.isNotBlank(pageVO.getScene()), Indicator::getScenes, "''")
                        .apply(StrUtil.isNotBlank(pageVO.getScene()), "JSON_CONTAINS(scenes, '\"" + pageVO.getScene() + "\"')")

        );
    }

    PageResult<IndicatorDTO> selectPage0(IndicatorPageVO pageVO);

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
