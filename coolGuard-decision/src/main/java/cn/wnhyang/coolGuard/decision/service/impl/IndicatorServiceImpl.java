package cn.wnhyang.coolGuard.decision.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.constant.FieldCode;
import cn.wnhyang.coolGuard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.decision.context.FieldContext;
import cn.wnhyang.coolGuard.decision.context.IndicatorContext;
import cn.wnhyang.coolGuard.decision.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.decision.convert.IndicatorVersionConvert;
import cn.wnhyang.coolGuard.decision.dto.IndicatorDTO;
import cn.wnhyang.coolGuard.decision.entity.Indicator;
import cn.wnhyang.coolGuard.decision.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.decision.enums.IndicatorType;
import cn.wnhyang.coolGuard.decision.enums.WinSize;
import cn.wnhyang.coolGuard.decision.indicator.IndicatorFactory;
import cn.wnhyang.coolGuard.decision.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.decision.mapper.IndicatorVersionMapper;
import cn.wnhyang.coolGuard.decision.service.CondService;
import cn.wnhyang.coolGuard.decision.service.IndicatorService;
import cn.wnhyang.coolGuard.decision.vo.IndicatorVO;
import cn.wnhyang.coolGuard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolGuard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolGuard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.decision.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.IndicatorUpdateVO;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.*;

/**
 * 指标表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@LiteflowComponent
public class IndicatorServiceImpl implements IndicatorService {

    private final IndicatorMapper indicatorMapper;

    private final IndicatorVersionMapper indicatorVersionMapper;

    private final CondService condService;

    private final AsyncTaskExecutor asyncTaskExecutor;

    private final IndicatorFactory indicatorFactory;

    public IndicatorServiceImpl(
            IndicatorMapper indicatorMapper,
            IndicatorVersionMapper indicatorVersionMapper,
            CondService condService,
            @Qualifier("indicatorAsync") AsyncTaskExecutor asyncTaskExecutor,
            IndicatorFactory indicatorFactory) {
        this.indicatorMapper = indicatorMapper;
        this.indicatorVersionMapper = indicatorVersionMapper;
        this.condService = condService;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.indicatorFactory = indicatorFactory;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.INDICATOR, allEntries = true)
    public Long createIndicator(IndicatorCreateVO createVO) {
        if (indicatorMapper.selectByName(createVO.getName()) != null) {
            throw exception(INDICATOR_NAME_EXIST);
        }
        Indicator indicator = IndicatorConvert.INSTANCE.convert(createVO);
        indicator.setCode(IdUtil.fastSimpleUUID());
        indicator.setReturnType(IndicatorType.getReturnType(indicator.getType(), indicator.getCalcField()));
        indicator.setTimeSlice(WinSize.getWinSizeValue(indicator.getWinSize()));
        indicatorMapper.insert(indicator);
        return indicator.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.INDICATOR, allEntries = true)
    public void updateIndicator(IndicatorUpdateVO updateVO) {
        Indicator indicator = indicatorMapper.selectById(updateVO.getId());
        if (indicator == null) {
            throw exception(INDICATOR_NOT_EXIST);
        }
        Indicator byName = indicatorMapper.selectByName(updateVO.getName());
        if (byName != null && !indicator.getId().equals(byName.getId())) {
            throw exception(INDICATOR_NAME_EXIST);
        }
        IndicatorUpdateVO converted2Update = IndicatorConvert.INSTANCE.convert2Update(getIndicator(updateVO.getId()));
        if (updateVO.equals(converted2Update)) {
            throw exception(INDICATOR_NOT_CHANGE);
        }
        Indicator convert = IndicatorConvert.INSTANCE.convert(updateVO);
        convert.setPublish(Boolean.FALSE);
        indicatorMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.INDICATOR, allEntries = true)
    public void deleteIndicator(Long id) {
        Indicator indicator = indicatorMapper.selectById(id);
        if (indicator == null) {
            throw exception(INDICATOR_NOT_EXIST);
        }
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectLatestByCode(indicator.getCode());
        if (indicatorVersion != null) {
            throw exception(INDICATOR_IS_RUNNING);
        }
        // TODO 查找引用
        indicatorMapper.deleteById(id);
        // 删除历史版本
        indicatorVersionMapper.deleteByCode(indicator.getCode());
    }

    @Override
    public IndicatorVO getIndicator(Long id) {
        Indicator indicator = indicatorMapper.selectById(id);
        return IndicatorConvert.INSTANCE.convert(indicator);
    }

    @Override
    public PageResult<IndicatorVO> pageIndicator(IndicatorPageVO pageVO) {
        PageResult<IndicatorDTO> indicatorPageResult = indicatorMapper.selectPage(pageVO);
        return IndicatorConvert.INSTANCE.convert2(indicatorPageResult);
    }

    @Override
    public List<IndicatorVO> listIndicator() {
        return IndicatorConvert.INSTANCE.convert(indicatorMapper.selectList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VersionSubmitResultVO submit(VersionSubmitVO submitVO) {
        VersionSubmitResultVO result = new VersionSubmitResultVO().setId(submitVO.getId());
        // 提交后就同时在version表中增加一条记录，表示该指标在运行状态
        Indicator indicator = indicatorMapper.selectById(submitVO.getId());
        if (indicator == null) {
            throw exception(INDICATOR_NOT_EXIST);
        }
        if (indicator.getPublish()) {
            throw exception(INDICATOR_VERSION_EXIST);
        }
        // 1、更新当前指标为已提交
        indicatorMapper.updateById(new Indicator().setId(indicator.getId()).setPublish(Boolean.TRUE));
        // 2、查询是否有已运行的，有版本+1，没有版本1
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectLatestVersionByCode(indicator.getCode());
        int version = 1;
        if (indicatorVersion != null) {
            version = indicatorVersion.getVersion() + 1;
            // 关闭已运行的
            indicatorVersionMapper.updateById(new IndicatorVersion().setId(indicatorVersion.getId()).setLatest(Boolean.FALSE));
        }
        // 3、插入新纪录并加入chain
        IndicatorVersion convert = IndicatorVersionConvert.INSTANCE.convert(indicator);
        convert.setVersion(version);
        convert.setVersionDesc(submitVO.getVersionDesc());
        convert.setLatest(Boolean.TRUE);
        indicatorVersionMapper.insert(convert);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<VersionSubmitResultVO> batchSubmit(BatchVersionSubmit submitVO) {
        List<VersionSubmitResultVO> result = new ArrayList<>();
        submitVO.getIds().forEach(id -> {
            try {
                result.add(submit(new VersionSubmitVO().setId(id).setVersionDesc(submitVO.getVersionDesc())));
            } catch (Exception e) {
                log.error("指标提交失败，id:{}", id, e);
                result.add(new VersionSubmitResultVO().setId(id).setSuccess(Boolean.FALSE).setMsg(e.getMessage()));
            }
        });
        return result;
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(indicatorMapper.selectList(), Indicator::getLabelValue);
    }

    @Override
    public void indicatorCompute() {
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        String appName = fieldContext.getData(FieldCode.APP_NAME, String.class);
        String policySetCode = fieldContext.getData(FieldCode.POLICY_SET_CODE, String.class);
        List<IndicatorContext.IndicatorCtx> indicatorCtxList = IndicatorVersionConvert.INSTANCE.convert2Ctx(indicatorVersionMapper.selectLatestListByScenes(appName, policySetCode));
        log.info("appName({}),policySetCode({})指标计算{}条", appName, policySetCode, indicatorCtxList.size());

        DecisionContextHolder.setIndicatorContext(new IndicatorContext());
        List<CompletableFuture<Void>> completableFutureList = indicatorCtxList.stream()
                .map(indicatorCtx ->
                        CompletableFuture.runAsync(() -> indicatorCompute(indicatorCtx),
                                asyncTaskExecutor))
                .toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();
    }

    @Override
    public void indicatorCompute(IndicatorContext.IndicatorCtx indicatorCtx) {
        boolean compute = indicatorFactory.getIndicator(indicatorCtx.getType()).compute(condService.cond(indicatorCtx.getCond()), indicatorCtx);
        log.info("指标(code:{}, name:{}, value:{})", indicatorCtx.getCode(), indicatorCtx.getName(), indicatorCtx.getValue());
        if (compute) {
            DecisionContextHolder.getIndicatorContext().setIndicator(indicatorCtx);
        }

    }

}
