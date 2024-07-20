package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.PolicyMode;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@LiteflowComponent
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleMapper ruleMapper;

    private final PolicyMapper policyMapper;

    private final ChainMapper chainMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRule(RuleCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(createVO);
        ruleMapper.insert(rule);

        Policy policy = policyMapper.selectById(rule.getPolicyId());
        String pChain = StrUtil.format(LFUtil.POLICY_CHAIN, policy.getId());
        if (!chainMapper.selectByChainName(pChain)) {
            String mode = policy.getMode();
            Chain chain = new Chain().setChainName(pChain);
            if (PolicyMode.WORST.equals(mode) || PolicyMode.WEIGHT.equals(mode)) {
                chain.setElData(StrUtil.format(LFUtil.WHEN_EL,
                        LFUtil.getNodeWithTag(LFUtil.RULE_COMMON_NODE, rule.getId())));
            } else if (PolicyMode.ORDER.equals(mode)) {
                chain.setElData(StrUtil.format(LFUtil.THEN_EL,
                        LFUtil.getNodeWithTag(LFUtil.RULE_COMMON_NODE, rule.getId())));
            }
            chainMapper.insert(chain);
        } else {
            Chain chain = chainMapper.getByChainName(pChain);
            chain.setElData(LFUtil.elAdd(chain.getElData(),
                    LFUtil.getNodeWithTag(LFUtil.RULE_COMMON_NODE, rule.getId())));
            chainMapper.updateByChainName(pChain, chain);
        }
        // TODO 创建规则chain，即IF
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getId());
        chainMapper.insert(new Chain().setChainName(rChain));
        return rule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRule(RuleUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(updateVO);
        ruleMapper.updateById(rule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_ACCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public boolean ruleAccess(NodeComponent bindCmp) {
        Rule rule = ruleMapper.selectById(bindCmp.getTag());
        return !RuleStatus.OFF.equals(rule.getStatus());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void rulProcess(NodeComponent bindCmp) {
        bindCmp.invoke2Resp(StrUtil.format(LFUtil.RULE_CHAIN, bindCmp.getTag()), null);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_TRUE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void ruleTrue(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        Rule rule = ruleMapper.selectById(tag);
        RuleVO ruleVO = RuleConvert.INSTANCE.convert(rule);
        log.info("命中规则(id:{}, name:{}, code:{})", ruleVO.getId(), ruleVO.getName(), ruleVO.getCode());
        policyContext.addRuleVO(ruleVO.getPolicyId(), ruleVO);
        // TODO 后置操作，除了处置方式外

    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_FALSE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void ruleFalse(NodeComponent bindCmp) {
        log.info("规则未命中");
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
