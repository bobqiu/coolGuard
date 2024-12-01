package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.mapper.RuleVersionMapper;
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

    private final RuleVersionMapper ruleVersionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public Long createRule(RuleCreateVO createVO) {
        if (ruleMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(RULE_CODE_EXIST);
        }
        Rule rule = RuleConvert.INSTANCE.convert(createVO);
        ruleMapper.insert(rule);

        String condEl = LFUtil.buildCondEl(createVO.getCond());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode());
        chainMapper.insert(new Chain().setChainName(rChain).setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.RULE_TRUE_COMMON_NODE,
                LFUtil.RULE_FALSE_COMMON_NODE)));
        // 创建版本
        ruleVersionMapper.insert(new RuleVersion().setCode(rule.getCode()).setRule(rule).setStatus(Boolean.TRUE));
        return rule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void updateRule(RuleUpdateVO updateVO) {
        Rule rule = ruleMapper.selectById(updateVO.getId());
        if (rule == null) {
            throw exception(RULE_NOT_EXIST);
        }
        Rule byCode = ruleMapper.selectByCode(updateVO.getCode());
        if (byCode != null && !byCode.getId().equals(updateVO.getId())) {
            throw exception(RULE_CODE_EXIST);
        }
        Rule convert = RuleConvert.INSTANCE.convert(updateVO);
        ruleMapper.updateById(convert);
        String condEl = LFUtil.buildCondEl(updateVO.getCond());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, convert.getCode());
        Chain chain = chainMapper.getByChainName(rChain);
        chain.setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                LFUtil.RULE_TRUE_COMMON_NODE,
                LFUtil.RULE_FALSE_COMMON_NODE));
        chainMapper.updateById(chain);
        // 确认是否有更改
        RuleVersion ruleVersion = ruleVersionMapper.selectLatest(convert.getCode());
        // 有更改，1、旧版本状态改为旧版本，2、创建新版本版本号为新版本号+1
        if (!convert.equals(ruleVersion.getRule())) {
            ruleVersionMapper.updateById(new RuleVersion().setId(ruleVersion.getId()).setStatus(Boolean.FALSE));
            ruleVersionMapper.insert(new RuleVersion().setCode(convert.getCode()).setRule(convert).setStatus(Boolean.TRUE).setVersion(ruleVersion.getVersion() + 1));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void deleteRule(Long id) {
        Rule rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw exception(RULE_NOT_EXIST);
        }
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
            // 删除所有版本
            ruleVersionMapper.deleteBySetCode(rule.getCode());
        });
    }

    @Override
    public RuleVO getRule(Long id) {
        Rule rule = ruleMapper.selectById(id);
        return RuleConvert.INSTANCE.convert(rule);
    }

    @Override
    public PageResult<RuleVO> pageRule(RulePageVO pageVO) {
        PageResult<Rule> rulePageResult = ruleMapper.selectPage(pageVO);

        return RuleConvert.INSTANCE.convert(rulePageResult);
    }

    @Override
    public List<RuleVO> listByPolicyCode(String policyCode) {
        List<Rule> ruleList = ruleMapper.selectByPolicyCode(policyCode);
        return RuleConvert.INSTANCE.convert(ruleList);
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

}
