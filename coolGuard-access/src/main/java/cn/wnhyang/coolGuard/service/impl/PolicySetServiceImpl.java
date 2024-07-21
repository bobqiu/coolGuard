package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.context.AccessResponse;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.convert.PolicySetConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyService;
import cn.wnhyang.coolGuard.service.PolicySetService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.PolicySetVO;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPolicySet(PolicySetCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(createVO);
        policySetMapper.insert(policySet);

        if (!chainMapper.selectByChainName(LFUtil.POLICY_SET_ROUTE_CHAIN)) {
            Chain chain = new Chain().setChainName(LFUtil.POLICY_SET_ROUTE_CHAIN)
                    .setElData(StrUtil.format(LFUtil.WHEN_EMPTY_NODE_EL,
                            LFUtil.getNodeWithTag(LFUtil.POLICY_SET_COMMON_NODE, policySet.getId())));
            chainMapper.insert(chain);
        } else {
            Chain chain = chainMapper.getByChainName(LFUtil.POLICY_SET_ROUTE_CHAIN);
            chain.setElData(LFUtil.elAdd(chain.getElData(),
                    LFUtil.getNodeWithTag(LFUtil.POLICY_SET_COMMON_NODE, policySet.getId())));
            chainMapper.updateByChainName(LFUtil.POLICY_SET_ROUTE_CHAIN, chain);
        }
        // TODO 创建chain
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getId());
        chainMapper.insert(new Chain().setChainName(psChain));
        return policySet.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePolicySet(PolicySetUpdateVO updateVO) {
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(updateVO);
        policySetMapper.updateById(policySet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePolicySet(Long id) {
        validateExists(id);
        deletePolicySet(Collections.singleton(id));
    }

    @Override
    public void deletePolicySet(Collection<Long> ids) {
        ids.forEach(id -> {
            // 1、确认是否还有运行的策略
            List<Policy> policyList = policyMapper.selectRunningListBySetId(id);
            if (CollUtil.isNotEmpty(policyList)) {
                throw exception(POLICY_SET_REFERENCE);
            }
            PolicySet policySet = policySetMapper.selectById(id);
            // 2、没有运行的策略就可以删除策略集了
            // 3、删除策略集下的所有规则
            policyList = policyMapper.selectListBySetId(id);
            policyService.deletePolicy(CollectionUtils.convertSet(policyList, Policy::getId));
            // 4、删除chain
            String psChain = LFUtil.POLICY_SET_ROUTE_CHAIN;
            Chain chain = chainMapper.getByChainName(psChain);
            chain.setElData(LFUtil.removeEl(chain.getElData(),
                    LFUtil.getNodeWithTag(LFUtil.POLICY_SET_COMMON_NODE, id)));
            chainMapper.updateByChainName(psChain, chain);
            policySetMapper.deleteById(id);
            chainMapper.deleteByChainName(StrUtil.format(LFUtil.POLICY_SET_CHAIN, id));
        });
    }

    @Override
    public PolicySetVO getPolicySet(Long id) {
        PolicySet policySet = policySetMapper.selectById(id);
        PolicySetVO policySetVO = PolicySetConvert.INSTANCE.convert(policySet);
        List<Policy> policyList = policyMapper.selectListBySetId(id);
        List<PolicyVO> strategies = PolicyConvert.INSTANCE.convert(policyList);
        policySetVO.setPolicyList(strategies);
        return policySetVO;
    }

    @Override
    public PageResult<PolicySetVO> pagePolicySet(PolicySetPageVO pageVO) {
        // 1、查询规则所属策略
        List<Long> policyIdList = ruleMapper.selectPolicyId(pageVO.getRuleName(), pageVO.getRuleCode());

        // 2、查询策略所属策略集
        List<Policy> policyList = policyMapper.selectList(policyIdList, pageVO.getPolicyName(), pageVO.getPolicyCode());

        // 3、过滤策略集
        Set<Long> policySetIdSet = policyList.stream().map(Policy::getPolicySetId).collect(Collectors.toSet());

        List<PolicySet> policySetList = policySetMapper.selectList(policySetIdSet, pageVO.getAppName(), pageVO.getName(), pageVO.getCode());

        List<PolicySetVO> policySetVOList = PolicySetConvert.INSTANCE.convert(policySetList);

        // 策略集拼装策略
        List<PolicySetVO> collect = policySetVOList.stream()
                .skip((long) (pageVO.getPageNo() - 1) * pageVO.getPageSize())
                .limit(pageVO.getPageSize())
                .peek(item -> {
                    List<Policy> strategies = policyList.stream().filter(policy -> item.getId().equals(policy.getPolicySetId()))
                            .toList();
                    List<PolicyVO> policyVOList = PolicyConvert.INSTANCE.convert(strategies);
                    item.setPolicyList(policyVOList);
                }).collect(Collectors.toList());

        return new PageResult<>(collect, (long) policySetList.size());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_ACCESS, nodeId = LFUtil.POLICY_SET_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public boolean policySetAccess(NodeComponent bindCmp) {
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);
        String tag = bindCmp.getTag();

        String appName = accessRequest.getStringData(FieldName.appName);
        String policySetCode = accessRequest.getStringData(FieldName.policySetCode);

        // 查询策略集
        PolicySet policySet = policySetMapper.selectByAppNameAndCode(appName, policySetCode);
        if (tag.equals(policySet.getId().toString())) {
            return policySet.getStatus();
        }
        return false;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.POLICY_SET_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public void policySetRoute(NodeComponent bindCmp) {

        PolicySet policySet = policySetMapper.selectById(bindCmp.getTag());

        log.info("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})", policySet.getAppName(), policySet.getCode(), policySet.getName());
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicySetVO policySetVO = PolicySetConvert.INSTANCE.convert(policySet);
        policyContext.setPolicySetVO(policySetVO);

        // TODO 策略集决策流，默认并行策略运行

        bindCmp.invoke2Resp(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getId()), null);

        AccessResponse accessResponse = bindCmp.getContextBean(AccessResponse.class);

        accessResponse.setPolicySetResult(policyContext.convert());
        log.info("策略集(name:{})执行完毕", policyContext.getPolicySetVO().getName());
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
        PolicySet policySet = policySetMapper.selectById(id);
        if (policySet == null) {
            throw exception(POLICY_SET_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        PolicySet policySet = policySetMapper.selectByCode(code);
        if (policySet == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(POLICY_SET_CODE_EXIST);
        }
        if (!policySet.getId().equals(id)) {
            throw exception(POLICY_SET_CODE_EXIST);
        }
    }

}
