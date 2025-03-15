package cn.wnhyang.coolguard.decision.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.decision.constant.PolicyMode;
import cn.wnhyang.coolguard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolguard.decision.context.PolicyContext;
import cn.wnhyang.coolguard.decision.convert.PolicyConvert;
import cn.wnhyang.coolguard.decision.convert.PolicyVersionConvert;
import cn.wnhyang.coolguard.decision.convert.RuleConvert;
import cn.wnhyang.coolguard.decision.dto.PolicyDTO;
import cn.wnhyang.coolguard.decision.entity.Policy;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.entity.Rule;
import cn.wnhyang.coolguard.decision.entity.RuleVersion;
import cn.wnhyang.coolguard.decision.mapper.*;
import cn.wnhyang.coolguard.decision.service.PolicyService;
import cn.wnhyang.coolguard.decision.service.RuleService;
import cn.wnhyang.coolguard.decision.util.LFUtil;
import cn.wnhyang.coolguard.decision.vo.PolicyVO;
import cn.wnhyang.coolguard.decision.vo.RuleVO;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.PolicyCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicyPageVO;
import cn.wnhyang.coolguard.decision.vo.update.PolicyUpdateVO;
import cn.wnhyang.coolguard.redis.constant.RedisKey;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.*;

/**
 * 策略表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@LiteflowComponent
public class PolicyServiceImpl implements PolicyService {

    private final PolicyMapper policyMapper;

    private final PolicyVersionMapper policyVersionMapper;

    private final ChainMapper chainMapper;

    private final RuleMapper ruleMapper;

    private final RuleVersionMapper ruleVersionMapper;

    private final RuleService ruleService;

    private final AsyncTaskExecutor asyncTaskExecutor;

    public PolicyServiceImpl(PolicyMapper policyMapper,
                             PolicyVersionMapper policyVersionMapper,
                             ChainMapper chainMapper,
                             RuleMapper ruleMapper,
                             RuleVersionMapper ruleVersionMapper,
                             RuleService ruleService,
                             @Qualifier("policyAsync") AsyncTaskExecutor asyncTaskExecutor) {
        this.policyMapper = policyMapper;
        this.policyVersionMapper = policyVersionMapper;
        this.chainMapper = chainMapper;
        this.ruleMapper = ruleMapper;
        this.ruleVersionMapper = ruleVersionMapper;
        this.ruleService = ruleService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY, allEntries = true)
    public Long createPolicy(PolicyCreateVO createVO) {
        if (policyMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(POLICY_CODE_EXIST);
        }
        if (policyMapper.selectByName(createVO.getName()) != null) {
            throw exception(POLICY_NAME_EXIST);
        }
        Policy policy = PolicyConvert.INSTANCE.convert(createVO);
        policyMapper.insert(policy);
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
        Policy byName = policyMapper.selectByName(updateVO.getName());
        if (byName != null && !policy.getId().equals(byName.getId())) {
            throw exception(POLICY_NAME_EXIST);
        }
        Policy convert = PolicyConvert.INSTANCE.convert(updateVO);
        convert.setPublish(Boolean.FALSE);
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
        // 1、确认策略是否在运行
        PolicyVersion policyVersion = policyVersionMapper.selectLatestByCode(policy.getCode());
        if (policyVersion != null) {
            throw exception(POLICY_IS_RUNNING);
        }
        // 2、确认是否被策略集编排引用
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policy.getPolicySetCode());
        if (chainMapper.selectByChainNameAndEl(psChain, LFUtil.getNodeWithTag(LFUtil.POLICY_NODE, policy.getCode()))) {
            throw exception(POLICY_REFERENCE_BY_POLICY_SET_DELETE);
        }
        // 3、确认是否还有运行的规则
        List<RuleVersion> ruleVersionList = ruleVersionMapper.selectLatestByPolicyCode(policy.getCode());
        if (CollUtil.isNotEmpty(ruleVersionList)) {
            throw exception(POLICY_REFERENCE_RULE_DELETE);
        }
        // 3、删除策略下的所有规则
        ruleService.deleteRule(CollectionUtils.convertSet(ruleMapper.selectListByPolicyCode(policy.getCode()), Rule::getId));
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
        List<Rule> ruleList = ruleMapper.selectListByPolicyCode(policy.getCode());
        List<RuleVO> ruleVOList = RuleConvert.INSTANCE.convert(ruleList);
        policyVO.setRuleList(ruleVOList);
        return policyVO;
    }

    @Override
    public PageResult<PolicyVO> pagePolicy(PolicyPageVO pageVO) {
        PageResult<PolicyDTO> policyPageResult = policyMapper.selectPage(pageVO);
        PageResult<PolicyVO> policyVOPageResult = PolicyConvert.INSTANCE.convert2(policyPageResult);
        policyVOPageResult.getList().forEach(policyVO ->
                policyVO.setRuleList(RuleConvert.INSTANCE.convert(ruleMapper.selectListByPolicyCode(policyVO.getCode()))));
        return policyVOPageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(VersionSubmitVO submitVO) {
        Policy policy = policyMapper.selectById(submitVO.getId());
        // 确认策略集是否存在
        if (policy == null) {
            throw exception(POLICY_NOT_EXIST);
        }
        // 确认策略集是否已发布
        if (policy.getPublish()) {
            throw exception(POLICY_VERSION_EXIST);
        }
        // 1、更新当前策略为已提交
        policyMapper.updateById(new Policy().setId(policy.getId()).setPublish(Boolean.TRUE));
        // 2、查询是否有已运行的
        PolicyVersion policyVersion = policyVersionMapper.selectLatestVersion(policy.getCode());
        int version = 1;
        if (policyVersion != null) {
            version = policyVersion.getVersion() + 1;
            // 关闭已运行的
            policyVersionMapper.updateById(new PolicyVersion().setId(policyVersion.getId()).setLatest(Boolean.FALSE));
        }
        // 3、插入新纪录并加入chain
        PolicyVersion convert = PolicyVersionConvert.INSTANCE.convert(policy);
        convert.setVersion(version);
        convert.setVersionDesc(submitVO.getVersionDesc());
        convert.setLatest(Boolean.TRUE);
        policyVersionMapper.insert(convert);
        // 4、策略不需要chain
    }

    @Override
    public List<PolicyVO> listByPolicySetCode(String setCode) {
        List<Policy> policyList = policyMapper.selectListBySetCode(setCode);
        return PolicyConvert.INSTANCE.convert(policyList);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(policyMapper.selectList(), Policy::getLabelValue);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.POLICY_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "策略组件")
    public void policy(NodeComponent bindCmp) {
        String policyCode = bindCmp.getTag();
        PolicyContext policyContext = DecisionContextHolder.getPolicyContext();
        PolicyContext.PolicyCtx policyCtx = PolicyConvert.INSTANCE.convert2Ctx(policyVersionMapper.selectLatestByCode(policyCode));
        policyContext.addPolicy(policyCtx.getCode(), policyCtx);
        List<PolicyContext.RuleCtx> ruleCtxList = RuleConvert.INSTANCE.convert2Ctx(ruleVersionMapper.selectLatestRunningByPolicyCode(policyCode));

        bindCmp.setStepData(StrUtil.format("当前策略(code:{}, name:{}, mode:{}, 规则数量:{})", policyCtx.getCode(), policyCtx.getName(), policyCtx.getMode(), ruleCtxList.size()));
        log.info("当前策略(code:{}, name:{}, mode:{}, 规则数量:{})", policyCtx.getCode(), policyCtx.getName(), policyCtx.getMode(), ruleCtxList.size());

        if (PolicyMode.ORDER.equals(policyCtx.getMode())) {
            for (PolicyContext.RuleCtx ruleCtx : ruleCtxList) {
                ruleService.executeRule(ruleCtx);
                if (policyContext.isHitRisk(policyCtx.getCode())) {
                    break;
                }
            }
        } else {
            List<CompletableFuture<Void>> completableFutureList = ruleCtxList.stream().map(ruleCtx -> CompletableFuture.runAsync(
                            () -> ruleService.executeRule(ruleCtx),
                            asyncTaskExecutor))
                    .toList();
            CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();
        }
    }

}
