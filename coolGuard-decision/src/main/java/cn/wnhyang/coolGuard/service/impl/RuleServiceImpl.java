package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.StrategyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.RULE_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.RULE_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 规则表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleMapper ruleMapper;

    @Override
    public Long createRule(RuleCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(createVO);
        ruleMapper.insert(rule);
        return rule.getId();
    }

    @Override
    public void updateRule(RuleUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(updateVO);
        ruleMapper.updateById(rule);
    }

    @Override
    public void deleteRule(Long id) {
        validateExists(id);
        ruleMapper.deleteById(id);
    }

    @Override
    public Rule getRule(Long id) {
        return ruleMapper.selectById(id);
    }

    @Override
    public PageResult<Rule> pageRule(RulePageVO pageVO) {
        return ruleMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "rulePre", nodeType = NodeTypeEnum.COMMON)
    public void rulePre(NodeComponent bindCmp) {
        log.info("规则预处理");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "ruleProcess", nodeType = NodeTypeEnum.COMMON)
    public void ruleProcess(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        StrategyContext strategyContext = bindCmp.getContextBean(StrategyContext.class);
        Rule rule = ruleMapper.selectById(tag);
        RuleVO ruleVO = RuleConvert.INSTANCE.convert(rule);
        log.info("命中规则(id:{}, name:{}, code:{})", ruleVO.getId(), ruleVO.getName(), ruleVO.getCode());
        strategyContext.addRuleVO(ruleVO.getStrategyId(), ruleVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "ruleFalse", nodeType = NodeTypeEnum.COMMON)
    public void ruleFalse(NodeComponent bindCmp) {
        log.info("规则未命中");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "ruleFinally", nodeType = NodeTypeEnum.COMMON)
    public void ruleFinally(NodeComponent bindCmp) {
        log.info("规则执行结束");
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
        Rule rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw exception(RULE_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        Rule rule = ruleMapper.selectByCode(code);
        if (rule == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(RULE_CODE_EXIST);
        }
        if (!rule.getId().equals(id)) {
            throw exception(RULE_CODE_EXIST);
        }
    }

}
