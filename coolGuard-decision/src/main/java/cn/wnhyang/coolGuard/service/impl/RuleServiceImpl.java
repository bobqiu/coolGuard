package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.PolicyMode;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.entity.*;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.JsonUtil;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.util.QLExpressUtil;
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
import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
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

    private final ChainMapper chainMapper;

    private final RuleVersionMapper ruleVersionMapper;

    public static String buildRuleBingoEl(Action action) {
        return LFUtil.buildWhen(LFUtil.RULE_TRUE,
                LFUtil.buildElWithData(LFUtil.ADD_TAG, JsonUtil.toJsonString(action.getAddTags())),
                LFUtil.buildElWithData(LFUtil.ADD_LIST_DATA, JsonUtil.toJsonString(action.getAddLists())),
                LFUtil.buildElWithData(LFUtil.SEND_SMS, JsonUtil.toJsonString(action.getSendSms())),
                LFUtil.buildElWithData(LFUtil.SET_FIELD, JsonUtil.toJsonString(action.getSetFields())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public Long createRule(RuleCreateVO createVO) {
        if (ruleMapper.selectByRuleId(createVO.getRuleId()) != null) {
            throw exception(RULE_RULE_ID_EXIST);
        }
        if (ruleMapper.selectByName(createVO.getName()) != null) {
            throw exception(RULE_NAME_EXIST);
        }
        Rule rule = RuleConvert.INSTANCE.convert(createVO);
        rule.setCode(IdUtil.fastSimpleUUID());
        ruleMapper.insert(rule);

        String condEl = LFUtil.buildCondEl(createVO.getCond());
        String rTrue = buildRuleBingoEl(createVO.getRuleTrue());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode());
        chainMapper.insert(new Chain().setChainName(rChain).setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                rTrue,
                LFUtil.RULE_FALSE)));
        // 创建版本
        ruleVersionMapper.insert(new RuleVersion().setCode(rule.getCode()).setRule(rule).setLatest(Boolean.TRUE));
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
        Rule byRuleId = ruleMapper.selectByRuleId(updateVO.getRuleId());
        if (byRuleId != null && !byRuleId.getId().equals(updateVO.getId())) {
            throw exception(RULE_RULE_ID_EXIST);
        }
        Rule byName = ruleMapper.selectByName(updateVO.getName());
        if (byName != null && !rule.getId().equals(byName.getId())) {
            throw exception(RULE_NAME_EXIST);
        }
        Rule convert = RuleConvert.INSTANCE.convert(updateVO);
        ruleMapper.updateById(convert);
        String condEl = LFUtil.buildCondEl(updateVO.getCond());
        String rTrue = buildRuleBingoEl(updateVO.getRuleTrue());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, convert.getCode());
        Chain chain = chainMapper.getByChainName(rChain);
        chain.setElData(StrUtil.format(LFUtil.IF_EL, condEl,
                rTrue,
                LFUtil.RULE_FALSE));
        chainMapper.updateById(chain);
        // 确认是否有更改
        RuleVersion ruleVersion = ruleVersionMapper.selectLatest(convert.getCode());
        // 有更改，1、旧版本状态改为旧版本，2、创建新版本版本号为新版本号+1
        if (!convert.equals(ruleVersion.getRule())) {
            ruleVersionMapper.updateById(new RuleVersion().setId(ruleVersion.getId()).setLatest(Boolean.FALSE));
            ruleVersionMapper.insert(new RuleVersion().setCode(convert.getCode()).setRule(convert).setLatest(Boolean.TRUE).setVersion(ruleVersion.getVersion() + 1));
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
        // 1、确认策略集是否还在运行
        RuleVersion ruleVersion = ruleVersionMapper.selectLatest(rule.getCode());
        if (ruleVersion != null) {
            throw exception(RULE_IS_RUNNING);
        }
        ruleMapper.deleteById(id);
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode()));
        // 删除所有版本
        ruleVersionMapper.deleteBySetCode(rule.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void deleteRule(Collection<Long> ids) {
        ids.forEach(this::deleteRule);
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

    @Override
    public void submit(Long id) {

    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(ruleMapper.selectList(), Rule::getLabelValue);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_ACCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON)
    public boolean ruleAccess(NodeComponent bindCmp) {
        String policyCode = bindCmp.getSubChainReqData();
        int index = bindCmp.getLoopIndex();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyContext.RuleCtx rule = policyContext.getRule(policyCode, index);
        return !RuleStatus.OFF.equals(rule.getStatus());
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则普通组件")
    public void rulProcess(NodeComponent bindCmp) {
        String policyCode = bindCmp.getSubChainReqData();
        int index = bindCmp.getLoopIndex();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyContext.RuleCtx rule = policyContext.getRule(policyCode, index);
        bindCmp.invoke2Resp(StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode()), rule);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_TRUE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则true组件")
    public void ruleTrue(NodeComponent bindCmp) {
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyContext.RuleCtx rule = bindCmp.getSubChainReqData();
        log.info("命中规则(name:{}, code:{})", rule.getName(), rule.getCode());
        if (RuleStatus.MOCK.equals(rule.getStatus())) {
            policyContext.addHitMockRuleVO(rule.getPolicyCode(), rule);
        } else {
            // 权重
            if (PolicyMode.WEIGHT.equals(policyContext.getPolicy(rule.getPolicyCode()).getMode())) {
                try {
                    Double value = (Double) QLExpressUtil.execute(rule.getExpress(), bindCmp.getContextBean(FieldContext.class));
                    rule.setExpressValue(value);
                } catch (Exception e) {
                    log.error("规则表达式执行异常", e);
                }
            }
            policyContext.addHitRuleVO(rule.getPolicyCode(), rule);
        }
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.RULE_FALSE, nodeType = NodeTypeEnum.COMMON, nodeName = "规则false组件")
    public void ruleFalse(NodeComponent bindCmp) {
        log.info("规则未命中");
    }

}
