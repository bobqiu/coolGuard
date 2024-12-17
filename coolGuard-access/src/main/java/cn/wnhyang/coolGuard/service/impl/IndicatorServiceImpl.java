package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.convert.IndicatorVersionConvert;
import cn.wnhyang.coolGuard.entity.*;
import cn.wnhyang.coolGuard.enums.WinSize;
import cn.wnhyang.coolGuard.indicator.AbstractIndicator;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorVersionMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.util.IndicatorUtil;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.BatchVersionSubmitResultVO;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorByPolicySetPageVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 指标表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@LiteflowComponent
public class IndicatorServiceImpl implements IndicatorService {

    private static final Map<String, AbstractIndicator> INDICATOR_MAP = new HashMap<>();

    private final IndicatorMapper indicatorMapper;

    private final PolicySetMapper policySetMapper;

    private final ChainMapper chainMapper;

    private final IndicatorVersionMapper indicatorVersionMapper;

    public IndicatorServiceImpl(List<AbstractIndicator> indicatorList, IndicatorMapper indicatorMapper, PolicySetMapper policySetMapper, ChainMapper chainMapper, IndicatorVersionMapper indicatorVersionMapper) {
        addIndicator(indicatorList);
        this.indicatorMapper = indicatorMapper;
        this.policySetMapper = policySetMapper;
        this.chainMapper = chainMapper;
        this.indicatorVersionMapper = indicatorVersionMapper;
    }

    private void addIndicator(List<AbstractIndicator> indicatorList) {
        indicatorList.forEach(indicator -> INDICATOR_MAP.put(indicator.getTypeCode(), indicator));
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
        indicator.setReturnType(IndicatorUtil.getReturnType(indicator.getType(), indicator.getCalcField()));
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
    }

    @Override
    public IndicatorVO getIndicator(Long id) {
        Indicator indicator = indicatorMapper.selectById(id);
        return IndicatorConvert.INSTANCE.convert(indicator);
    }

    @Override
    public PageResult<IndicatorVO> pageIndicator(IndicatorPageVO pageVO) {
        PageResult<Indicator> indicatorPageResult = indicatorMapper.selectPage(pageVO);
        return IndicatorConvert.INSTANCE.convert(indicatorPageResult);
    }

    private Cond getCond(String code) {
        Chain chain = chainMapper.getByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, code));
        List<String> ifEl = LFUtil.parseIfEl(chain.getElData());
        return LFUtil.parseToCond(ifEl.get(0));
    }


    @Override
    public PageResult<Indicator> pageIndicatorByPolicySet(IndicatorByPolicySetPageVO pageVO) {
        String policySetCode = pageVO.getPolicySetCode();
        PolicySet policySet = policySetMapper.selectByCode(policySetCode);
        if (ObjectUtil.isNotNull(policySet)) {
            indicatorMapper.selectPageByScene(pageVO, SceneType.POLICY_SET, policySet.getCode());
        }
        return PageResult.empty();
    }

    @Override
    public List<IndicatorVO> listIndicator() {
        return IndicatorConvert.INSTANCE.convert(indicatorMapper.selectList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchVersionSubmitResultVO submit(VersionSubmitVO submitVO) {
        BatchVersionSubmitResultVO result = new BatchVersionSubmitResultVO().setId(submitVO.getId());
        // 提交后就同时在version表中增加一条记录，表示该指标在运行状态
        Indicator indicator = indicatorMapper.selectById(submitVO.getId());
        if (ObjectUtil.isNull(indicator)) {
            throw exception(INDICATOR_NOT_EXIST);
        }
        if (indicator.getPublish()) {
            throw exception(INDICATOR_VERSION_EXIST);
        }
        // 1、更新当前指标为已提交
        indicatorMapper.updateById(new Indicator().setId(submitVO.getId()).setPublish(Boolean.TRUE));
        // 2、查询是否有已运行的，有版本+1，没有版本1
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectLatestByCode(indicator.getCode());
        int version = 0;
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
        // 4、更新chain
        String iChain = StrUtil.format(LFUtil.INDICATOR_CHAIN, indicator.getCode());
        String condEl = LFUtil.buildCondEl(convert.getCond());
        if (chainMapper.selectByChainName(iChain)) {
            Chain chain = chainMapper.getByChainName(iChain);
            chain.setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                    LFUtil.INDICATOR_TRUE_COMMON_NODE,
                    LFUtil.INDICATOR_FALSE_COMMON_NODE));
            chainMapper.updateById(chain);
        } else {
            chainMapper.insert(new Chain().setChainName(iChain).setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                    LFUtil.INDICATOR_TRUE_COMMON_NODE,
                    LFUtil.INDICATOR_FALSE_COMMON_NODE)));
        }
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BatchVersionSubmitResultVO> batchSubmit(BatchVersionSubmit submitVO) {
        List<BatchVersionSubmitResultVO> result = new ArrayList<>();
        submitVO.getIds().forEach(id -> {
            try {
                result.add(submit(new VersionSubmitVO().setId(id).setVersionDesc(submitVO.getVersionDesc())));
            } catch (Exception e) {
                log.error("指标提交失败，id:{}", id, e);
                result.add(new BatchVersionSubmitResultVO().setId(id).setSuccess(Boolean.FALSE).setMsg(e.getMessage()));
            }
        });
        return result;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_FOR, nodeId = LFUtil.INDICATOR_FOR_NODE, nodeType = NodeTypeEnum.FOR, nodeName = "指标for组件")
    public int indicatorFor(NodeComponent bindCmp) {
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        String appName = fieldContext.getStringData(FieldName.appName);
        String policySetCode = fieldContext.getStringData(FieldName.policySetCode);
        List<IndicatorVersion> indicatorVersionList = indicatorVersionMapper.selectLatestListByScenes(appName, policySetCode);
        indicatorContext.setIndicatorList(ListUtil.toCopyOnWriteArrayList(IndicatorVersionConvert.INSTANCE.convert2Ctx(indicatorVersionList)));
        return indicatorVersionList.size();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "指标普通组件")
    public void indicator(NodeComponent bindCmp) {
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        int index = bindCmp.getLoopIndex();
        bindCmp.invoke2Resp(StrUtil.format(LFUtil.INDICATOR_CHAIN, indicatorContext.getIndicator(index).getCode()), index);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_TRUE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "指标true组件")
    public void indicatorTrue(NodeComponent bindCmp) {
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);

        int index = bindCmp.getSubChainReqData();
        IndicatorContext.IndicatorCtx indicatorCtx = indicatorContext.getIndicator(index);
        AbstractIndicator abstractIndicator = INDICATOR_MAP.get(indicatorCtx.getType());
        indicatorContext.setIndicatorValue(index, abstractIndicator.compute(true, indicatorCtx, fieldContext));
        log.info("true:指标(code:{}, name:{}, value:{})", indicatorCtx.getCode(), indicatorCtx.getName(), indicatorCtx.getValue());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_FALSE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "指标false组件")
    public void indicatorFalse(NodeComponent bindCmp) {
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);

        int index = bindCmp.getSubChainReqData();
        IndicatorContext.IndicatorCtx indicatorCtx = indicatorContext.getIndicator(index);
        indicatorContext.setIndicatorValue(index, INDICATOR_MAP.get(indicatorCtx.getType()).compute(false, indicatorCtx, fieldContext));
        log.info("false指标(code:{}, name:{}, value:{})", indicatorCtx.getCode(), indicatorCtx.getName(), indicatorCtx.getValue());

    }

}
