package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.enums.WinSize;
import cn.wnhyang.coolGuard.indicator.AbstractIndicator;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Service
public class IndicatorServiceImpl implements IndicatorService {

    private static final Map<String, AbstractIndicator> INDICATOR_MAP = new HashMap<>();

    private final IndicatorMapper indicatorMapper;

    public IndicatorServiceImpl(List<AbstractIndicator> indicatorList, IndicatorMapper indicatorMapper) {
        addIndicator(indicatorList);
        this.indicatorMapper = indicatorMapper;
    }

    private void addIndicator(List<AbstractIndicator> indicatorList) {
        indicatorList.forEach(indicator -> INDICATOR_MAP.put(indicator.getType(), indicator));
    }

    @Override
    public Long createIndicator(IndicatorCreateVO createVO) {
        Indicator indicator = IndicatorConvert.INSTANCE.convert(createVO);
        indicator.setTimeSlice(WinSize.getWinSizeValue(createVO.getWinSize()));
        log.info("createIndicator:{}", indicator);
        indicatorMapper.insert(indicator);
        return indicator.getId();
    }

    @Override
    public void updateIndicator(IndicatorUpdateVO updateVO) {
        Indicator indicator = IndicatorConvert.INSTANCE.convert(updateVO);
        indicatorMapper.updateById(indicator);
    }

    @Override
    public void deleteIndicator(Long id) {
        indicatorMapper.deleteById(id);
    }

    @Override
    public Indicator getIndicator(Long id) {
        return indicatorMapper.selectById(id);
    }

    @Override
    public PageResult<Indicator> pageIndicator(IndicatorPageVO pageVO) {
        return indicatorMapper.selectPage(pageVO);
    }

    @Override
    public void compute(List<Indicator> indicatorList, Map<String, String> eventDetail) {
        for (Indicator indicator : indicatorList) {
//            INDICATOR_MAP.get(indicator.getType()).compute(indicator, eventDetail);
        }
    }

    @Override
    public double getResultById(Long id, Map<String, String> eventDetail) {
        Indicator indicator = indicatorMapper.selectById(id);
        return getResult(indicator, eventDetail);
    }

    @Override
    public double getResult(Indicator indicator, Map<String, String> eventDetail) {
        return 0.0;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "indicatorProcess", nodeType = NodeTypeEnum.COMMON)
    public void indicatorProcess(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        IndicatorVO indicator = indicatorContext.getIndicator(Long.valueOf(tag));
        log.info("当前指标(id:{}, name:{})", indicator.getId(), indicator.getName());

        bindCmp.invoke2Resp(indicator.getChainName(), null);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "indicatorCompute", nodeType = NodeTypeEnum.COMMON)
    public void indicatorCompute(NodeComponent bindCmp) {
        // TODO 计算指标
        String tag = bindCmp.getTag();
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        IndicatorVO indicator = indicatorContext.getIndicator(Long.valueOf(tag));
        AbstractIndicator abstractIndicator = INDICATOR_MAP.get(indicator.getType());
        log.info("指标计算");
        abstractIndicator.compute(indicator, decisionRequest.getFields());
        double result = abstractIndicator.getResult();
        indicatorContext.setIndicatorValue(indicator.getId(), result);
        log.info("指标计算结束");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "indicatorFalse", nodeType = NodeTypeEnum.COMMON)
    public void indicatorFalse(NodeComponent bindCmp) {
        log.info("指标条件不满足");
    }

}
