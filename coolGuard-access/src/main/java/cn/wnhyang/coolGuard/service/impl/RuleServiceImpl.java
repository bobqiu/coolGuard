package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public Long createRule(RuleCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(createVO);
        ruleMapper.insert(rule);

        String condEl = LFUtil.buildCondEl(createVO.getCond());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode());
        chainMapper.insert(new Chain().setChainName(rChain).setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.RULE_TRUE_COMMON_NODE,
                LFUtil.RULE_FALSE_COMMON_NODE)));
        return rule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void updateRule(RuleUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getCode());
        Rule rule = RuleConvert.INSTANCE.convert(updateVO);
        ruleMapper.updateById(rule);
        String condEl = LFUtil.buildCondEl(updateVO.getCond());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode());
        Chain chain = chainMapper.getByChainName(rChain);
        chain.setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.RULE_TRUE_COMMON_NODE,
                LFUtil.RULE_FALSE_COMMON_NODE));
        chainMapper.updateById(chain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void deleteRule(Long id) {
        validateExists(id);
        deleteRule(Collections.singleton(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void deleteRule(Collection<Long> ids) {
        ids.forEach(id -> {
            Rule rule = ruleMapper.selectById(id);
            ruleMapper.deleteById(id);
            chainMapper.deleteByChainName(StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode()));
        });
    }

    @Override
    public RuleVO getRule(Long id) {
        Rule rule = ruleMapper.selectById(id);
        RuleVO ruleVO = RuleConvert.INSTANCE.convert(rule);
        ruleVO.setCond(getCond(rule.getCode()));
        return ruleVO;
    }

    @Override
    public PageResult<RuleVO> pageRule(RulePageVO pageVO) {
        PageResult<Rule> rulePageResult = ruleMapper.selectPage(pageVO);

        PageResult<RuleVO> voPageResult = RuleConvert.INSTANCE.convert(rulePageResult);

        voPageResult.getList().forEach(ruleVO -> ruleVO.setCond(getCond(ruleVO.getCode())));
        return voPageResult;
    }

    private Cond getCond(String code) {
        Chain chain = chainMapper.getByChainName(StrUtil.format(LFUtil.RULE_CHAIN, code));
        List<String> ifEl = LFUtil.parseIfEl(chain.getElData());
        return LFUtil.parseToCond(ifEl.get(0));
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_ACCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public boolean ruleAccess(NodeComponent bindCmp) {
        String policyCode = bindCmp.getSubChainReqData();
        int index = bindCmp.getLoopIndex();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        RuleVO ruleVO = policyContext.getRuleVO(policyCode, index);
        return !RuleStatus.OFF.equals(ruleVO.getStatus());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则普通组件")
    public void rulProcess(NodeComponent bindCmp) {
        String policyCode = bindCmp.getSubChainReqData();
        int index = bindCmp.getLoopIndex();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        RuleVO ruleVO = policyContext.getRuleVO(policyCode, index);
        bindCmp.invoke2Resp(StrUtil.format(LFUtil.RULE_CHAIN, ruleVO.getCode()), ruleVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_TRUE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则true组件")
    public void ruleTrue(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        RuleVO ruleVO = bindCmp.getSubChainReqData();
        log.info("命中规则(name:{}, code:{})", ruleVO.getName(), ruleVO.getCode());
        policyContext.addHitRuleVO(ruleVO.getPolicyCode(), ruleVO);
        // TODO 后置操作，除了处置方式外
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_FALSE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则false组件")
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
