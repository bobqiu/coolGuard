package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.context.StrategyContext;
import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.convert.StrategyConvert;
import cn.wnhyang.coolGuard.convert.StrategySetConvert;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.Strategy;
import cn.wnhyang.coolGuard.entity.StrategySet;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.StrategyMapper;
import cn.wnhyang.coolGuard.mapper.StrategySetMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.StrategySetService;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.StrategySetVO;
import cn.wnhyang.coolGuard.vo.StrategyVO;
import cn.wnhyang.coolGuard.vo.create.StrategySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategySetPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategySetUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.builder.el.WhenELWrapper;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.exception.LiteFlowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.STRATEGY_SET_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.STRATEGY_SET_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static com.yomahub.liteflow.builder.el.ELBus.node;

/**
 * 策略集表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StrategySetServiceImpl implements StrategySetService {

    private final StrategySetMapper strategySetMapper;

    private final StrategyMapper strategyMapper;

    private final IndicatorMapper indicatorMapper;

    @Override
    public Long createStrategySet(StrategySetCreateVO createVO) {
        StrategySet strategySet = StrategySetConvert.INSTANCE.convert(createVO);
        strategySetMapper.insert(strategySet);
        return strategySet.getId();
    }

    @Override
    public void updateStrategySet(StrategySetUpdateVO updateVO) {
        StrategySet strategySet = StrategySetConvert.INSTANCE.convert(updateVO);
        strategySetMapper.updateById(strategySet);
    }

    @Override
    public void deleteStrategySet(Long id) {
        strategySetMapper.deleteById(id);
    }

    @Override
    public StrategySet getStrategySet(Long id) {
        return strategySetMapper.selectById(id);
    }

    @Override
    public PageResult<StrategySet> pageStrategySet(StrategySetPageVO pageVO) {
        return strategySetMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategySetProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategySetProcess(NodeComponent bindCmp) {

        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        String appName = decisionRequest.getStringData("N_S_appName");
        String strategySetCode = decisionRequest.getStringData("N_S_strategySetCode");

        // 查询策略集
        StrategySet strategySet = strategySetMapper.selectByAppNameAndCode(appName, strategySetCode);

        if (strategySet == null) {
            log.error("应用名:{}, 策略集编码:{}, 对应的策略集不存在", appName, strategySetCode);
            throw new LiteFlowException("策略集不存在");
        }
        if (!strategySet.getStatus()) {
            log.error("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})未启用", appName, strategySetCode, strategySet.getName());
            throw new LiteFlowException("策略集未启用");
        }
        log.info("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})", appName, strategySetCode, strategySet.getName());
        StrategyContext strategyContext = bindCmp.getContextBean(StrategyContext.class);
        StrategySetVO strategySetVO = StrategySetConvert.INSTANCE.convert(strategySet);
        strategyContext.setStrategySetVO(strategySetVO);

        // TODO 计算指标
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        List<Indicator> indicatorListByAppName = indicatorMapper.selectListByScene(strategySetVO.getAppName(), SceneType.APP);
        List<Indicator> indicatorListByStrategySetCode = indicatorMapper.selectListByScene(strategySetVO.getCode(), SceneType.STRATEGY_SET);
        // 合并
        indicatorListByAppName.addAll(indicatorListByStrategySetCode);
        List<IndicatorVO> indicatorVOList = IndicatorConvert.INSTANCE.convert(indicatorListByAppName);
        for (IndicatorVO indicatorVO : indicatorVOList) {
            indicatorContext.addIndicator(indicatorVO.getId(), indicatorVO);
        }

        // 查询策略列表
        List<Strategy> strategyList = strategyMapper.selectListBySetId(strategySet.getId());

        // 策略列表为空
        if (CollUtil.isEmpty(strategyList)) {
            log.error("策略集(name:{})下没有策略", strategySet.getName());
            throw new LiteFlowException("策略集下没有策略");
        }
        WhenELWrapper when = new WhenELWrapper();
        for (Strategy strategy : strategyList) {
            if (strategy.getStatus().equals(1)) {
                when.when(
                        node("strategyProcess").tag(String.valueOf(strategy.getId()))
                );
                StrategyVO strategyVO = StrategyConvert.INSTANCE.convert(strategy);
                strategyContext.addStrategy(strategyVO.getId(), strategyVO);
            }
        }
        String el = when.toEL();
        log.info("策略集(name:{})下策略表达式:{}", strategySet.getName(), el);

        LiteFlowChainELBuilder.createChain().setChainId("strategyChain").setEL(
                // 输出el表达式
                when.toEL()
        ).build();

        bindCmp.invoke2Resp("strategyChain", null);

        DecisionResponse decisionResponse = bindCmp.getContextBean(DecisionResponse.class);

        decisionResponse.setStrategySetResult(strategyContext.convert());
        log.info("策略集(name:{})执行完毕", strategyContext.getStrategySetVO().getName());
    }

    private void validateForCreateOrUpdate(Long id, String name) {
        // 校验存在
        validateExists(id);
        // 校验名唯一
        validateCodeUnique(id, name);
    }

    private void validateExists(Long id) {
        if (id == null) {
            return;
        }
        StrategySet strategySet = strategySetMapper.selectById(id);
        if (strategySet == null) {
            throw exception(STRATEGY_SET_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        StrategySet strategySet = strategySetMapper.selectByCode(code);
        if (strategySet == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(STRATEGY_SET_CODE_EXIST);
        }
        if (!strategySet.getId().equals(id)) {
            throw exception(STRATEGY_SET_CODE_EXIST);
        }
    }

}
