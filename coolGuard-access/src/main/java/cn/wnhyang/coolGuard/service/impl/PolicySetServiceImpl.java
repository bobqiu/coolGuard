package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.context.AccessResponse;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.convert.PolicySetConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mapper.*;
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
import org.springframework.cache.annotation.CacheEvict;
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

    private final PolicySetVersionMapper policySetVersionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public Long createPolicySet(PolicySetCreateVO createVO) {
        if (policySetMapper.selectByCode(createVO.getName()) != null) {
            throw exception(POLICY_SET_CODE_EXIST);
        }
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(createVO);
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getCode());
        // TODO 策略集el，默认并行
        String elData = createVO.getChain().isBlank() ? LFUtil.WHEN_EMPTY_NODE : createVO.getChain();
        chainMapper.insert(new Chain().setChainName(psChain).setElData(elData));
        policySet.setChain(elData);
        policySetMapper.insert(policySet);
        // 创建版本
        policySetVersionMapper.insert(new PolicySetVersion().setCode(policySet.getCode()).setStatus(Boolean.TRUE).setChain(policySet.getChain()));
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
        PolicySet convert = PolicySetConvert.INSTANCE.convert(updateVO);
        if (!convert.getStatus()) {
            List<Policy> policyList = policyMapper.selectRunningListBySetCode(convert.getCode());
            if (CollUtil.isNotEmpty(policyList)) {
                throw exception(POLICY_SET_REFERENCE_UPDATE);
            }
        }
        String elData = updateVO.getChain().isBlank() ? LFUtil.WHEN_EMPTY_NODE : updateVO.getChain();
        String psChain = StrUtil.format(LFUtil.POLICY_SET_CHAIN, convert.getCode());
        Chain chain = chainMapper.getByChainName(psChain);
        chain.setElData(elData);
        chainMapper.updateById(chain);
        policySetMapper.updateById(convert);
        // 确认是否有更改
        PolicySetVersion policySetVersion = policySetVersionMapper.selectLatest(policySet.getCode());
        // 有更改，1、旧版本状态改为旧版本，2、创建新版本版本号为新版本号+1
        if (!policySetVersion.getChain().equals(elData)) {
            policySetVersionMapper.updateById(new PolicySetVersion().setId(policySetVersion.getId()).setStatus(Boolean.FALSE));
            policySetVersionMapper.insert(new PolicySetVersion().setStatus(Boolean.TRUE).setVersion(policySetVersion.getVersion() + 1).setChain(elData));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public void deletePolicySet(Long id) {
        PolicySet policySet = policySetMapper.selectById(id);
        if (policySet == null) {
            throw exception(POLICY_SET_NOT_EXIST);
        }
        deletePolicySet(Collections.singleton(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.POLICY_SET, allEntries = true)
    public void deletePolicySet(Collection<Long> ids) {
        ids.forEach(id -> {
            PolicySet policySet = policySetMapper.selectById(id);
            // 1、确认是否还有运行的策略
            List<Policy> policyList = policyMapper.selectRunningListBySetCode(policySet.getCode());
            if (CollUtil.isNotEmpty(policyList)) {
                throw exception(POLICY_SET_REFERENCE_DELETE);
            }
            // 2、没有运行的策略就可以删除策略集了
            // 3、删除策略集下的所有规则
            policyList = policyMapper.selectListBySetCode(policySet.getCode());
            policyService.deletePolicy(CollectionUtils.convertSet(policyList, Policy::getId));
            // 4、删除chain
            policySetMapper.deleteById(id);
            chainMapper.deleteByChainName(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getCode()));
            // 5、删除所有版本
            policySetVersionMapper.deleteBySetCode(policySet.getCode());
        });
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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.POLICY_SET_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "策略集普通组件")
    public void policySet(NodeComponent bindCmp) {
        // TODO 策略集下策略默认并行，运行时判断有无配置Chain，有则运行，没有则for并行
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);
        String appName = accessRequest.getStringData(FieldName.appName);
        String policySetCode = accessRequest.getStringData(FieldName.policySetCode);

        PolicySet policySet = policySetMapper.selectByAppNameAndCode(appName, policySetCode);
        if (policySet != null && policySet.getStatus()) {
            log.info("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})", policySet.getAppName(), policySet.getCode(), policySet.getName());
            PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
            policyContext.setPolicySetVO(PolicySetConvert.INSTANCE.convert(policySet));

            bindCmp.invoke2Resp(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySet.getCode()), null);

            AccessResponse accessResponse = bindCmp.getContextBean(AccessResponse.class);
            accessResponse.setPolicySetResult(policyContext.convert());
            log.info("策略集(name:{})执行完毕", policySet.getName());
        } else {
            log.info("未匹配应用名:{}, 策略集编码:{}", appName, policySetCode);
        }
    }

}
