package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.context.AccessResponse;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.convert.PolicySetConvert;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetService;
import cn.wnhyang.coolGuard.vo.PolicySetVO;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.builder.el.WhenELWrapper;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.exception.LiteFlowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.POLICY_SET_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.POLICY_SET_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static com.yomahub.liteflow.builder.el.ELBus.node;

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

    private final IndicatorMapper indicatorMapper;

    @Override
    public Long createPolicySet(PolicySetCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(createVO);
        policySetMapper.insert(policySet);
        return policySet.getId();
    }

    @Override
    public void updatePolicySet(PolicySetUpdateVO updateVO) {
        PolicySet policySet = PolicySetConvert.INSTANCE.convert(updateVO);
        policySetMapper.updateById(policySet);
    }

    @Override
    public void deletePolicySet(Long id) {
        // TODO 有引用不可删除
        policySetMapper.deleteById(id);
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

    // TODO 未来替换成路由
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "policySetRoute", nodeType = NodeTypeEnum.COMMON)
    public void policySetRoute(NodeComponent bindCmp) {

        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);

        String appName = accessRequest.getStringData(FieldName.appName);
        String policySetCode = accessRequest.getStringData(FieldName.policySetCode);

        // 查询策略集
        PolicySet policySet = policySetMapper.selectByAppNameAndCode(appName, policySetCode);

        if (policySet == null) {
            log.error("应用名:{}, 策略集编码:{}, 对应的策略集不存在", appName, policySetCode);
            throw new LiteFlowException("策略集不存在");
        }
        if (!policySet.getStatus()) {
            log.error("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})未启用", appName, policySetCode, policySet.getName());
            throw new LiteFlowException("策略集未启用");
        }
        log.info("应用名:{}, 策略集编码:{}, 对应的策略集(name:{})", appName, policySetCode, policySet.getName());
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicySetVO policySetVO = PolicySetConvert.INSTANCE.convert(policySet);
        policyContext.setPolicySetVO(policySetVO);

        // TODO 策略集决策流
        // 查询策略列表
        List<Policy> policyList = policyMapper.selectListBySetId(policySet.getId());

        // 策略列表为空
        if (CollUtil.isEmpty(policyList)) {
            log.error("策略集(name:{})下没有策略", policySet.getName());
            throw new LiteFlowException("策略集下没有策略");
        }
        WhenELWrapper when = new WhenELWrapper();
        for (Policy policy : policyList) {
            // TODO 策略状态
            if (policy.getStatus().equals(1)) {
                when.when(
                        node("policyProcess").tag(String.valueOf(policy.getId()))
                );
                PolicyVO policyVO = PolicyConvert.INSTANCE.convert(policy);
                policyContext.addPolicy(policyVO.getId(), policyVO);
            }
        }
        String el = when.toEL();
        log.debug("策略集(name:{})下策略表达式:{}", policySet.getName(), el);

        LiteFlowChainELBuilder.createChain().setChainId("policyChain").setEL(
                // 输出el表达式
                when.toEL()
        ).build();

        bindCmp.invoke2Resp("policyChain", null);

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
