package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.PolicyMode;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.convert.PolicyVersionConvert;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.dto.PolicyDTO;
import cn.wnhyang.coolGuard.entity.*;
import cn.wnhyang.coolGuard.mapper.*;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyService;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
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

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.*;
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

    private final PolicyVersionMapper policyVersionMapper;

    private final ChainMapper chainMapper;

    private final RuleMapper ruleMapper;

    private final RuleVersionMapper ruleVersionMapper;

    private final RuleService ruleService;

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
        if (chainMapper.selectByChainNameAndEl(psChain, LFUtil.getNodeWithTag(LFUtil.POLICY_COMMON_NODE, policy.getCode()))) {
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
        // 删除历史版本
        policyVersionMapper.deleteByCode(policy.getCode());
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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.POLICY_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "策略普通组件")
    public void policy(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        log.info("当前策略(code:{})", tag);
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyContext.PolicyCtx policy = PolicyConvert.INSTANCE.convert2Ctx(policyVersionMapper.selectLatestByCode(tag));
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
        List<PolicyContext.RuleCtx> ruleList = RuleConvert.INSTANCE.convert2Ctx(ruleVersionMapper.selectLatestByPolicyCode(policyCode));
        log.info("当前策略(code:{})下的规则数量为:{}", policyCode, ruleList.size());
        policyContext.addRuleList(policyCode, ruleList);
        return ruleList.size();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = LFUtil.POLICY_BREAK_NODE, nodeType = NodeTypeEnum.BOOLEAN, nodeName = "策略break组件")
    public boolean policyBreak(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        String policyCode = bindCmp.getSubChainReqData();
        return policyContext.isHitRisk(policyCode);
    }

}
