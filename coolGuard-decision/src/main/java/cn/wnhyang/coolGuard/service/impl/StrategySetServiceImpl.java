package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
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
import cn.wnhyang.coolGuard.mapper.RuleMapper;
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
import com.yomahub.liteflow.annotation.LiteflowComponent;
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
import java.util.Set;
import java.util.stream.Collectors;

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
@LiteflowComponent
@RequiredArgsConstructor
public class StrategySetServiceImpl implements StrategySetService {

    private final StrategySetMapper strategySetMapper;

    private final StrategyMapper strategyMapper;

    private final RuleMapper ruleMapper;

    private final IndicatorMapper indicatorMapper;

    @Override
    public Long createStrategySet(StrategySetCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
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
        // TODO 有引用不可删除
        strategySetMapper.deleteById(id);
    }

    @Override
    public StrategySetVO getStrategySet(Long id) {
        StrategySet strategySet = strategySetMapper.selectById(id);
        StrategySetVO strategySetVO = StrategySetConvert.INSTANCE.convert(strategySet);
        List<Strategy> strategyList = strategyMapper.selectListBySetId(id);
        List<StrategyVO> strategies = StrategyConvert.INSTANCE.convert(strategyList);
        strategySetVO.setStrategyList(strategies);
        return strategySetVO;
    }

    @Override
    public PageResult<StrategySetVO> pageStrategySet(StrategySetPageVO pageVO) {
        // 1、查询规则所属策略
        List<Long> strategyIdList = ruleMapper.selectStrategyId(pageVO.getRuleName(), pageVO.getRuleCode());

        // 2、查询策略所属策略集
        List<Strategy> strategyList = strategyMapper.selectList(strategyIdList, pageVO.getStrategyName(), pageVO.getStrategyCode());

        // 3、过滤策略集
        Set<Long> strategySetIdSet = strategyList.stream().map(Strategy::getStrategySetId).collect(Collectors.toSet());

        List<StrategySet> strategySetList = strategySetMapper.selectList(strategySetIdSet, pageVO.getAppName(), pageVO.getName(), pageVO.getCode());

        List<StrategySetVO> strategySetVOList = StrategySetConvert.INSTANCE.convert(strategySetList);

        // 策略集拼装策略
        List<StrategySetVO> collect = strategySetVOList.stream()
                .skip((long) (pageVO.getPageNo() - 1) * pageVO.getPageSize())
                .limit(pageVO.getPageSize())
                .peek(item -> {
                    List<Strategy> strategies = strategyList.stream().filter(strategy -> item.getId().equals(strategy.getStrategySetId()))
                            .toList();
                    List<StrategyVO> strategyVOList = StrategyConvert.INSTANCE.convert(strategies);
                    item.setStrategyList(strategyVOList);
                }).collect(Collectors.toList());

        return new PageResult<>(collect, (long) strategySetList.size());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategySetProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategySetProcess(NodeComponent bindCmp) {

        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        String appName = decisionRequest.getStringData(FieldName.appName);
        String strategySetCode = decisionRequest.getStringData(FieldName.strategySetCode);

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

        // 计算指标
        IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
        List<Indicator> indicatorList = indicatorMapper.selectList(strategySetVO.getAppName(), strategySetVO.getCode());
        List<IndicatorVO> indicatorVOList = IndicatorConvert.INSTANCE.convert(indicatorList);
        WhenELWrapper whenI = new WhenELWrapper();
        for (IndicatorVO indicatorVO : indicatorVOList) {
            whenI.when(
                    node("indicatorProcess").tag(String.valueOf(indicatorVO.getId()))
            );
            indicatorContext.addIndicator(indicatorVO.getId(), indicatorVO);
        }

        LiteFlowChainELBuilder.createChain().setChainId("indicatorChain").setEL(
                // 输出el表达式
                whenI.toEL()
        ).build();

        bindCmp.invoke2Resp("indicatorChain", null);


        // 查询策略列表
        List<Strategy> strategyList = strategyMapper.selectListBySetId(strategySet.getId());

        // 策略列表为空
        if (CollUtil.isEmpty(strategyList)) {
            log.error("策略集(name:{})下没有策略", strategySet.getName());
            throw new LiteFlowException("策略集下没有策略");
        }
        WhenELWrapper when = new WhenELWrapper();
        for (Strategy strategy : strategyList) {
            // TODO 策略状态
            if (strategy.getStatus().equals(1)) {
                when.when(
                        node("strategyProcess").tag(String.valueOf(strategy.getId()))
                );
                StrategyVO strategyVO = StrategyConvert.INSTANCE.convert(strategy);
                strategyContext.addStrategy(strategyVO.getId(), strategyVO);
            }
        }
        String el = when.toEL();
        log.debug("策略集(name:{})下策略表达式:{}", strategySet.getName(), el);

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
