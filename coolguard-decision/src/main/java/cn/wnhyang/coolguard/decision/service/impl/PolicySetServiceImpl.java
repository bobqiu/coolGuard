package cn.wnhyang.coolguard.decision.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.decision.constant.FieldCode;
import cn.wnhyang.coolguard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolguard.decision.context.EventContext;
import cn.wnhyang.coolguard.decision.context.FieldContext;
import cn.wnhyang.coolguard.decision.context.PolicyContext;
import cn.wnhyang.coolguard.decision.convert.DisposalConvert;
import cn.wnhyang.coolguard.decision.convert.PolicyConvert;
import cn.wnhyang.coolguard.decision.convert.PolicySetConvert;
import cn.wnhyang.coolguard.decision.convert.PolicySetVersionConvert;
import cn.wnhyang.coolguard.decision.dto.PolicySetDTO;
import cn.wnhyang.coolguard.decision.entity.*;
import cn.wnhyang.coolguard.decision.mapper.*;
import cn.wnhyang.coolguard.decision.service.PolicyService;
import cn.wnhyang.coolguard.decision.service.PolicySetService;
import cn.wnhyang.coolguard.decision.util.LFUtil;
import cn.wnhyang.coolguard.decision.vo.PolicySetVO;
import cn.wnhyang.coolguard.decision.vo.PolicyVO;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetPageVO;
import cn.wnhyang.coolguard.decision.vo.update.PolicySetUpdateVO;
import cn.wnhyang.coolguard.redis.constant.RedisKey;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.*;

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
public class PolicySetServiceImpl implements PolicySetService {

    private final PolicySetMapper policySetMapper;

    private final PolicyMapper policyMapper;

    private final RuleMapper ruleMapper;

    private final ChainMapper chainMapper;

    private final PolicyService policyService;

    private final PolicyVersionMapper policyVersionMapper;

    private final PolicySetVersionMapper policySetVersionMapper;

    private final DisposalMapper disposalMapper;

    private final FlowExecutor flowExecutor;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public Long createPolicySet(PolicySetCreateVO createVO) {
        if (policySetMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(POLICY_SET_CODE_EXIST);
        }
        if (policySetMapper.selectByName(createVO.getName()) != null) {
            throw exception(POLICY_SET_NAME_EXIST);
        }
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(createVO);
        // TODO json解析到el
        // TODO 注意区分这里的chain并非el，而是图的json数据需要转换为el
        policySetMapper.insert(policySet);
        return policySet.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public void updatePolicySet(PolicySetUpdateVO updateVO) {
        PolicySet policySet = policySetMapper.selectById(updateVO.getId());
        if (policySet == null) {
            throw exception(POLICY_SET_NOT_EXIST);
        }
        PolicySet byName = policySetMapper.selectByName(updateVO.getName());
        if (byName != null && !policySet.getId().equals(byName.getId())) {
            throw exception(POLICY_SET_NAME_EXIST);
        }
        PolicySet convert = PolicySetConvert.INSTANCE.convert(updateVO);
        convert.setPublish(Boolean.FALSE);
        policySetMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public void deletePolicySet(Long id) {
        PolicySet policySet = policySetMapper.selectById(id);
        if (policySet == null) {
            throw exception(POLICY_SET_NOT_EXIST);
        }
        // 1、确认策略集是否还在运行
        PolicySetVersion policySetVersion = policySetVersionMapper.selectLatest(policySet.getCode());
        if (policySetVersion != null) {
            throw exception(POLICY_SET_IS_RUNNING);
        }
        // 2、确认是否还有运行的策略
        List<PolicyVersion> policyVersionList = policyVersionMapper.selectLatestBySetCode(policySet.getCode());
        if (CollUtil.isNotEmpty(policyVersionList)) {
            throw exception(POLICY_SET_REFERENCE_DELETE);
        }
        // 3、没有运行的策略就可以删除策略集了
        // 4、删除策略集下的所有策略
        policyService.deletePolicy(CollectionUtils.convertSet(policyMapper.selectListBySetCode(policySet.getCode()), Policy::getId));
        // 5、删除chain
        policySetMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public void deletePolicySet(Collection<Long> ids) {
        ids.forEach(this::deletePolicySet);
    }

    @Override
    public PolicySetVO getPolicySet(Long id) {
        PolicySet policySet = policySetMapper.selectById(id);
        PolicySetVO policySetVO = PolicySetConvert.INSTANCE.convert(policySet);
        List<Policy> policyList = policyMapper.selectListBySetCode(policySet.getCode());
        List<PolicyVO> strategies = PolicyConvert.INSTANCE.convert(policyList);
        policySetVO.setPolicyList(strategies);
        return policySetVO;
    }

    @Override
    public PageResult<PolicySetVO> pagePolicySet0(PolicySetPageVO pageVO) {
        PageResult<PolicySetDTO> policySetPageResult = policySetMapper.selectPage(pageVO);
        return PolicySetConvert.INSTANCE.convert2(policySetPageResult);
    }

    @Override
    public PageResult<PolicySetVO> pagePolicySet(PolicySetPageVO pageVO) {
        // 1、查询规则所属策略
        List<String> policyCodeList = ruleMapper.selectPolicyCodeList(pageVO.getRuleName(), pageVO.getRuleCode());

        // 2、查询策略所属策略集
        List<Policy> policyList = policyMapper.selectList(policyCodeList, pageVO.getPolicyName(), pageVO.getPolicyCode());

        // 3、过滤策略集
        Set<String> policySetCodeSet = policyList.stream().map(Policy::getPolicySetCode).collect(Collectors.toSet());

        List<PolicySet> policySetList = policySetMapper.selectList(policySetCodeSet, pageVO.getAppName(), pageVO.getName(), pageVO.getCode());

        List<PolicySetVO> policySetVOList = PolicySetConvert.INSTANCE.convert(policySetList);

        // 策略集拼装策略
        List<PolicySetVO> collect = policySetVOList.stream()
                .skip((long) (pageVO.getPageNo() - 1) * pageVO.getPageSize())
                .limit(pageVO.getPageSize())
                .peek(item -> {
                    List<Policy> strategies = policyList.stream().filter(policy -> item.getCode().equals(policy.getPolicySetCode()))
                            .toList();
                    List<PolicyVO> policyVOList = PolicyConvert.INSTANCE.convert(strategies);
                    item.setPolicyList(policyVOList);
                }).collect(Collectors.toList());

        return new PageResult<>(collect, (long) policySetList.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(VersionSubmitVO submitVO) {
        PolicySet policySet = policySetMapper.selectById(submitVO.getId());
        // 确认策略集是否存在
        if (policySet == null) {
            throw exception(POLICY_SET_NOT_EXIST);
        }
        // 确认策略集是否已发布
        if (policySet.getPublish()) {
            throw exception(POLICY_SET_VERSION_EXIST);
        }
        // 1、更新当前策略集为已提交
        policySetMapper.updateById(new PolicySet().setId(policySet.getId()).setPublish(Boolean.TRUE));
        // 2、查询是否有已运行的
        PolicySetVersion policySetVersion = policySetVersionMapper.selectLatestVersion(policySet.getCode());
        int version = 1;
        if (policySetVersion != null) {
            version = policySetVersion.getVersion() + 1;
            // 关闭已运行的
            policySetVersionMapper.updateById(new PolicySetVersion().setId(policySetVersion.getId()).setLatest(Boolean.FALSE));
        }
        // 3、插入新纪录并加入chain
        PolicySetVersion convert = PolicySetVersionConvert.INSTANCE.convert(policySet);
        convert.setVersion(version);
        convert.setVersionDesc(submitVO.getVersionDesc());
        convert.setLatest(Boolean.TRUE);
        policySetVersionMapper.insert(convert);
        // 4、更新chain
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getCode());
        // TODO 注意区分这里的chain并非el，而是json画图的数据需要转换为el，同时检查el是否正确
        String elData = convert.getChain();
        if (chainMapper.selectByChainName(psChain)) {
            Chain chain = chainMapper.getByChainName(psChain);
            chain.setElData(elData);
            chainMapper.updateById(chain);
        } else {
            chainMapper.insert(new Chain().setChainName(psChain).setElData(elData));
        }
    }

    @Override
    public List<PolicySetVO> listPolicySet() {
        return PolicySetConvert.INSTANCE.convert(policySetMapper.selectList());
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(policySetMapper.selectList(), PolicySet::getLabelValue);
    }

    @Override
    public void policySet() {
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        String appName = fieldContext.getData(FieldCode.APP_NAME, String.class);
        String policySetCode = fieldContext.getData(FieldCode.POLICY_SET_CODE, String.class);

        PolicySetVersion policySetVersion = policySetVersionMapper.selectLatestByAppNameAndCode(appName, policySetCode);
        if (policySetVersion != null) {
            log.info("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})", policySetVersion.getAppName(), policySetVersion.getCode(), policySetVersion.getName());
            PolicyContext policyContext = new PolicyContext();
            PolicyContext.PolicySetCtx policySetCtx = PolicySetConvert.INSTANCE.convert2Ctx(policySetVersion);
            policyContext.init(DisposalConvert.INSTANCE.convert2Ctx(disposalMapper.selectList()), policySetCtx);
            log.info("开始执行策略集(code:{}, name:{})", policySetCode, policySetVersion.getName());

            DecisionContextHolder.setPolicyContext(policyContext);
            LiteflowResponse liteflowResponse = flowExecutor.execute2Resp(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySetVersion.getCode()), null);
            if (!liteflowResponse.isSuccess()) {
                throw exception(Integer.valueOf(liteflowResponse.getCode()), liteflowResponse.getMessage());
            }
            liteflowResponse.getExecuteStepQueue().forEach(step -> {
                log.info("nodeId: {}, nodeName:{}, tag:{}, timeSpent:{}, stepData:{}",
                        step.getNodeId(), step.getNodeName(), step.getTag(), step.getTimeSpent(), step.getStepData());
            });
            EventContext eventContext = DecisionContextHolder.getEventContext();
            eventContext.setPolicySetResult(policyContext.convert());
            log.info("执行策略集结束(code:{}, name:{})", policySetCode, policySetVersion.getName());
        } else {
            log.info("未匹配应用名:{}, 策略集编码:{}", appName, policySetCode);
        }
    }

}
