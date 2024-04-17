package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.StrategyContext;
import cn.wnhyang.coolGuard.convert.StrategyConvert;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.entity.Strategy;
import cn.wnhyang.coolGuard.enums.StrategyMode;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.mapper.StrategyMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.StrategyService;
import cn.wnhyang.coolGuard.vo.StrategyVO;
import cn.wnhyang.coolGuard.vo.create.StrategyCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategyPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategyUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.STRATEGY_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.STRATEGY_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 策略表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {

    private final StrategyMapper strategyMapper;

    private final RuleMapper ruleMapper;

    @Override
    public Long createStrategy(StrategyCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Strategy strategy = StrategyConvert.INSTANCE.convert(createVO);
        strategyMapper.insert(strategy);
        return strategy.getId();
    }

    @Override
    public void updateStrategy(StrategyUpdateVO updateVO) {
        Strategy strategy = StrategyConvert.INSTANCE.convert(updateVO);
        strategyMapper.updateById(strategy);
    }

    @Override
    public void deleteStrategy(Long id) {
        // TODO 有引用不可删除
        validateExists(id);
        strategyMapper.deleteById(id);
    }

    @Override
    public Strategy getStrategy(Long id) {
        return strategyMapper.selectById(id);
    }

    @Override
    public PageResult<Strategy> pageStrategy(StrategyPageVO pageVO) {
        return strategyMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategyProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategyProcess(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        StrategyContext strategyContext = bindCmp.getContextBean(StrategyContext.class);
        StrategyVO strategy = strategyContext.getStrategy(Long.valueOf(tag));
        log.info("当前策略(id:{}, name:{}, code:{})", strategy.getId(), strategy.getName(), strategy.getCode());

        List<Rule> ruleList = ruleMapper.selectByStrategyIdAndStatus(strategy.getId(), "1");
        if (CollUtil.isEmpty(ruleList)) {
            log.info("策略(name:{})下没有规则运行", strategy.getName());
            return;
        }
        strategyContext.initRuleList(strategy.getId());
        StrategyMode byMode = StrategyMode.getByMode(strategy.getMode());


        bindCmp.invoke2Resp(strategy.getChainName(), null);

    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategyOrderProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategyOrderProcess(NodeComponent bindCmp) {
        log.info("策略顺序");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategyWorstProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategyWorstProcess(NodeComponent bindCmp) {
        log.info("策略最坏");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "strategyWeightProcess", nodeType = NodeTypeEnum.COMMON)
    public void strategyWeightProcess(NodeComponent bindCmp) {
        log.info("策略权重");
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
        Strategy strategy = strategyMapper.selectById(id);
        if (strategy == null) {
            throw exception(STRATEGY_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        Strategy strategy = strategyMapper.selectByCode(code);
        if (strategy == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(STRATEGY_CODE_EXIST);
        }
        if (!strategy.getId().equals(id)) {
            throw exception(STRATEGY_CODE_EXIST);
        }
    }

}
