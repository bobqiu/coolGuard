package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.enums.WinSize;
import cn.wnhyang.coolGuard.indicator.AbstractIndicator;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public IndicatorServiceImpl(List<AbstractIndicator> indicatorList, IndicatorMapper indicatorMapper, PolicySetMapper policySetMapper, ChainMapper chainMapper) {
        addIndicator(indicatorList);
        this.indicatorMapper = indicatorMapper;
        this.policySetMapper = policySetMapper;
        this.chainMapper = chainMapper;
    }

    private void addIndicator(List<AbstractIndicator> indicatorList) {
        indicatorList.forEach(indicator -> INDICATOR_MAP.put(indicator.getType(), indicator));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createIndicator(IndicatorCreateVO createVO) {
        Indicator indicator = IndicatorConvert.INSTANCE.convert(createVO);
        indicator.setTimeSlice(WinSize.getWinSizeValue(createVO.getWinSize()));
        indicatorMapper.insert(indicator);

        String condEl = LFUtil.buildCondEl(createVO.getCond());
        String iChain = StrUtil.format(LFUtil.INDICATOR_CHAIN, indicator.getId());
        chainMapper.insert(new Chain().setChainName(iChain).setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.INDICATOR_TRUE_COMMON_NODE,
                LFUtil.INDICATOR_FALSE_COMMON_NODE)));
        return indicator.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIndicator(IndicatorUpdateVO updateVO) {
        Indicator indicator = IndicatorConvert.INSTANCE.convert(updateVO);
        indicatorMapper.updateById(indicator);
        String condEl = LFUtil.buildCondEl(updateVO.getCond());
        String iChain = StrUtil.format(LFUtil.INDICATOR_CHAIN, indicator.getId());
        Chain chain = chainMapper.getByChainName(iChain);
        chain.setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.INDICATOR_TRUE_COMMON_NODE,
                LFUtil.INDICATOR_FALSE_COMMON_NODE));
        chainMapper.updateById(chain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIndicator(Long id) {
        indicatorMapper.deleteById(id);
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, id));
    }

    @Override
    public IndicatorVO getIndicator(Long id) {
        Indicator indicator = indicatorMapper.selectById(id);
        IndicatorVO indicatorVO = IndicatorConvert.INSTANCE.convert(indicator);
        indicatorVO.setCond(getCond(id));
        return indicatorVO;
    }

    @Override
    public PageResult<IndicatorVO> pageIndicator(IndicatorPageVO pageVO) {
        PageResult<Indicator> indicatorPageResult = indicatorMapper.selectPageByScene(pageVO);

        PageResult<IndicatorVO> voPageResult = IndicatorConvert.INSTANCE.convert(indicatorPageResult);

        voPageResult.getList().forEach(indicatorVO -> indicatorVO.setCond(getCond(indicatorVO.getId())));
        return voPageResult;
    }

    private Cond getCond(Long id) {
        Chain chain = chainMapper.getByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, id));
        List<String> ifEl = LFUtil.parseIfEl(chain.getElData());
        return LFUtil.parseToCond(ifEl.get(0));
    }


    @Override
    public PageResult<Indicator> pageIndicatorByPolicySet(IndicatorByPolicySetPageVO pageVO) {
        Long policySetId = pageVO.getPolicySetId();
        PolicySet policySet = policySetMapper.selectById(policySetId);
        if (ObjectUtil.isNotNull(policySet)) {
            indicatorMapper.selectPageByScene(pageVO, SceneType.POLICY_SET, policySet.getCode());
        }
        return PageResult.empty();
    }

    @Override
    public List<Indicator> listIndicator() {
        return indicatorMapper.selectList();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_FOR, nodeId = LFUtil.INDICATOR_FOR_NODE, nodeType = NodeTypeEnum.FOR)
    public int indicatorFor(NodeComponent bindCmp) {
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        String appName = accessRequest.getStringData(FieldName.appName);
        String policySetCode = accessRequest.getStringData(FieldName.policySetCode);
        List<Indicator> indicators = indicatorMapper.selectListByScenes(appName, policySetCode);
        indicatorContext.setIndicatorList(ListUtil.toCopyOnWriteArrayList(IndicatorConvert.INSTANCE.convert(indicators)));
        return indicators.size();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_ACCESS, nodeId = LFUtil.INDICATOR_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public boolean indicatorAccess(NodeComponent bindCmp) {
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        int index = bindCmp.getLoopIndex();
        return indicatorContext.getIndicator(index).getStatus();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void indicator(NodeComponent bindCmp) {
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        int index = bindCmp.getLoopIndex();
        bindCmp.invoke2Resp(StrUtil.format(LFUtil.INDICATOR_CHAIN, indicatorContext.getIndicator(index).getId()), index);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_TRUE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void indicatorTrue(NodeComponent bindCmp) {
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);

        int index = bindCmp.getSubChainReqData();
        IndicatorVO indicatorVO = indicatorContext.getIndicator(index);
        indicatorContext.setIndicatorValue(index, INDICATOR_MAP.get(indicatorVO.getType()).compute(true, indicatorVO, accessRequest.getFields()));
        log.info("当前计算指标(id:{}, name:{}, value:{})", indicatorVO.getId(), indicatorVO.getName(), indicatorVO.getValue());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.INDICATOR_FALSE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void indicatorFalse(NodeComponent bindCmp) {
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);

        int index = bindCmp.getSubChainReqData();
        IndicatorVO indicatorVO = indicatorContext.getIndicator(index);
        indicatorContext.setIndicatorValue(index, INDICATOR_MAP.get(indicatorVO.getType()).compute(false, indicatorVO, accessRequest.getFields()));
        log.info("当前计算指标(id:{}, name:{}, value:{})", indicatorVO.getId(), indicatorVO.getName(), indicatorVO.getValue());

    }

}
