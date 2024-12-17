package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.PolicyMode;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyService;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicyUpdateVO;
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
import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
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
public class PolicyServiceImpl implements PolicyService {

    private final PolicyMapper policyMapper;

    private final PolicySetMapper policySetMapper;

    private final ChainMapper chainMapper;

    private final RuleMapper ruleMapper;

    private final RuleService ruleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY, allEntries = true)
    public Long createPolicy(PolicyCreateVO createVO) {
        if (policyMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(POLICY_CODE_EXIST);
        }
        Policy policy = PolicyConvert.INSTANCE.convert(createVO);
        policyMapper.insert(policy);

        // TODO 判断有没有策略流，没有并行加入
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policy.getPolicySetCode());
        Chain chain = chainMapper.getByChainName(psChain);
        chain.setElData(LFUtil.elAdd(chain.getElData(),
                LFUtil.getNodeWithTag(LFUtil.POLICY_COMMON_NODE, policy.getCode())));
        chainMapper.updateByChainName(psChain, chain);

        return policy.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY, allEntries = true)
    public void updatePolicy(PolicyUpdateVO updateVO) {
        Policy policy = policyMapper.selectById(updateVO.getId());
        if (policy == null) {
            throw exception(POLICY_NOT_EXIST);
        }
        Policy convert = PolicyConvert.INSTANCE.convert(updateVO);
        policyMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY, allEntries = true)
    public void deletePolicy(Long id) {
        Policy policy = policyMapper.selectById(id);
        if (policy == null) {
            throw exception(POLICY_NOT_EXIST);
        }
        // 1、确认是否还有运行的规则
        List<Rule> ruleList = ruleMapper.selectRunningListByPolicyCode(policy.getCode());
        if (CollUtil.isNotEmpty(ruleList)) {
            throw exception(POLICY_REFERENCE_DELETE);
        }
        // 2、没有运行的规则就可以删除策略了
        // 3、删除策略下的所有规则
        ruleList = ruleMapper.selectByPolicyCode(policy.getCode());
        ruleService.deleteRule(CollectionUtils.convertSet(ruleList, Rule::getId));
        // 4、删除chain
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policy.getPolicySetCode());
        Chain chain = chainMapper.getByChainName(psChain);
        chain.setElData(LFUtil.removeEl(chain.getElData(),
                LFUtil.getNodeWithTag(LFUtil.POLICY_COMMON_NODE, id)));
        chainMapper.updateByChainName(psChain, chain);
        policyMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY, allEntries = true)
    public void deletePolicy(Collection<Long> ids) {
        ids.forEach(this::deletePolicy);
    }

    @Override
    public PolicyVO getPolicy(Long id) {
        Policy policy = policyMapper.selectById(id);
        PolicyVO policyVO = PolicyConvert.INSTANCE.convert(policy);
        List<Rule> ruleList = ruleMapper.selectByPolicyCode(policy.getCode());
        List<RuleVO> ruleVOList = RuleConvert.INSTANCE.convert(ruleList);
        policyVO.setRuleList(ruleVOList);
        return policyVO;
    }

    @Override
    public PageResult<PolicyVO> pagePolicy(PolicyPageVO pageVO) {
        PageResult<Policy> policyPageResult = policyMapper.selectPage(pageVO);
        PageResult<PolicyVO> policyVOPageResult = PolicyConvert.INSTANCE.convert(policyPageResult);
        policyVOPageResult.getList().forEach(policyVO ->
                policyVO.setRuleList(RuleConvert.INSTANCE.convert(ruleMapper.selectByPolicyCode(policyVO.getCode()))));
        return policyVOPageResult;
    }

    @Override
    public List<PolicyVO> listByPolicySetCode(String setCode) {
        List<Policy> policyList = policyMapper.selectListBySetCode(setCode);
        return PolicyConvert.INSTANCE.convert(policyList);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.POLICY_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "策略普通组件")
    public void policy(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyContext.PolicyCtx policy = PolicyConvert.INSTANCE.convert2Ctx(policyMapper.selectByCode(bindCmp.getTag()));
        policyContext.addPolicy(policy.getCode(), policy);

        log.info("当前策略(code:{}, name:{}, code:{})", policy.getCode(), policy.getName(), policy.getCode());

        if (PolicyMode.ORDER.equals(policy.getMode())) {
            bindCmp.invoke2Resp(LFUtil.P_F, policy.getCode());
        } else {
            bindCmp.invoke2Resp(LFUtil.P_FP, policy.getCode());
        }
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_FOR, nodeId = LFUtil.POLICY_FOR_NODE, nodeType = NodeTypeEnum.FOR, nodeName = "策略for组件")
    public int policyFor(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        String policyCode = bindCmp.getSubChainReqData();
        List<PolicyContext.RuleCtx> ruleList = RuleConvert.INSTANCE.convert2Ctx(ruleMapper.selectByPolicyCode(policyCode));
        policyContext.addRuleList(policyCode, ruleList);
        return ruleList.size();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = LFUtil.POLICY_BREAK_NODE, nodeType = NodeTypeEnum.BOOLEAN, nodeName = "策略break组件")
    public boolean policyBreak(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        String policyCode = bindCmp.getSubChainReqData();
        return policyContext.isHit(policyCode);
    }

}
