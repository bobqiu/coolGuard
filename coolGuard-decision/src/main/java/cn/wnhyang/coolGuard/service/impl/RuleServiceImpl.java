package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.PolicyMode;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.RuleStatus;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.RuleConvert;
import cn.wnhyang.coolGuard.convert.RuleVersionConvert;
import cn.wnhyang.coolGuard.dto.RuleDTO;
import cn.wnhyang.coolGuard.entity.*;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.util.QLExpressUtil;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
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

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.*;
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
        if (action == null) {
            return LFUtil.RULE_TRUE;
        }
        return LFUtil.buildWhen(LFUtil.RULE_TRUE,
                LFUtil.buildElWithData(LFUtil.ADD_TAG, action.getAddTag()),
                LFUtil.buildElWithData(LFUtil.ADD_LIST_DATA, action.getAddList()),
                LFUtil.buildElWithData(LFUtil.SEND_SMS, action.getSendSms()),
                LFUtil.buildElWithData(LFUtil.SET_FIELD, action.getSetField()));
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
        convert.setPublish(Boolean.FALSE);
        ruleMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.RULE, allEntries = true)
    public void deleteRule(Long id) {
        Rule rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw exception(RULE_NOT_EXIST);
        }
        // 1、确认规则是否还在运行
        RuleVersion ruleVersion = ruleVersionMapper.selectLatestByCode(rule.getCode());
        if (ruleVersion != null) {
            throw exception(RULE_IS_RUNNING);
        }
        ruleMapper.deleteById(id);
        // 删除所有版本
        ruleVersionMapper.deleteByCode(rule.getCode());
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
        PageResult<RuleDTO> rulePageResult = ruleMapper.selectPage(pageVO);

        return RuleConvert.INSTANCE.convert2(rulePageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(VersionSubmitVO submitVO) {
        Rule rule = ruleMapper.selectById(submitVO.getId());
        // 确认策略集是否存在
        if (rule == null) {
            throw exception(RULE_NOT_EXIST);
        }
        // 确认策略集是否已发布
        if (rule.getPublish()) {
            throw exception(RULE_VERSION_EXIST);
        }
        // 1、更新当前规则为已提交
        ruleMapper.updateById(new Rule().setId(rule.getId()).setPublish(Boolean.TRUE));
        // 2、查询是否有已运行的
        RuleVersion ruleVersion = ruleVersionMapper.selectLatestVersion(rule.getCode());
        int version = 1;
        if (ruleVersion != null) {
            version = ruleVersion.getVersion() + 1;
            // 关闭已运行的
            ruleVersionMapper.updateById(new RuleVersion().setId(ruleVersion.getId()).setLatest(Boolean.FALSE));
        }
        // 3、插入新纪录并加入chain
        RuleVersion convert = RuleVersionConvert.INSTANCE.convert(rule);
        convert.setVersion(version);
        convert.setVersionDesc(submitVO.getVersionDesc());
        convert.setLatest(Boolean.TRUE);
        ruleVersionMapper.insert(convert);
        // 4、更新chain
        String condEl = LFUtil.buildCondEl(convert.getCond());
        String rTrue = buildRuleBingoEl(convert.getRuleTrue());
        String rChain = StrUtil.format(LFUtil.RULE_CHAIN, rule.getCode());
        String elData = StrUtil.format(LFUtil.IF_EL, condEl, rTrue, LFUtil.RULE_FALSE);
        if (chainMapper.selectByChainName(rChain)) {
            Chain chain = chainMapper.getByChainName(rChain);
            chain.setElData(elData);
            chainMapper.updateById(chain);
        } else {
            chainMapper.insert(new Chain().setChainName(rChain).setElData(elData));
        }
    }

    @Override
    public List<RuleVO> listByPolicyCode(String policyCode) {
        List<Rule> ruleList = ruleMapper.selectListByPolicyCode(policyCode);
        return RuleConvert.INSTANCE.convert(ruleList);
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
