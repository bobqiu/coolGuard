package cn.wnhyang.coolGuard.decision.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.constant.PolicyMode;
import cn.wnhyang.coolGuard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.decision.context.FieldContext;
import cn.wnhyang.coolGuard.decision.context.PolicyContext;
import cn.wnhyang.coolGuard.decision.convert.RuleConvert;
import cn.wnhyang.coolGuard.decision.convert.RuleVersionConvert;
import cn.wnhyang.coolGuard.decision.dto.RuleDTO;
import cn.wnhyang.coolGuard.decision.entity.Rule;
import cn.wnhyang.coolGuard.decision.entity.RuleVersion;
import cn.wnhyang.coolGuard.decision.mapper.RuleMapper;
import cn.wnhyang.coolGuard.decision.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.decision.service.*;
import cn.wnhyang.coolGuard.decision.util.QLExpressUtil;
import cn.wnhyang.coolGuard.decision.vo.RuleVO;
import cn.wnhyang.coolGuard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.decision.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.decision.vo.update.RuleUpdateVO;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.*;

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

    private final RuleVersionMapper ruleVersionMapper;

    private final CondService condService;

    private final TagService tagService;

    private final FieldService fieldService;

    private final ListDataService listDataService;

    private final SmsTemplateService smsTemplateService;

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

    @Override
    public void executeRule(PolicyContext.RuleCtx ruleCtx) {
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        PolicyContext policyContext = DecisionContextHolder.getPolicyContext();
        if (condService.cond(ruleCtx.getCond())) {
            if (PolicyMode.WEIGHT.equals(policyContext.getPolicy(ruleCtx.getPolicyCode()).getMode())) {
                try {
                    Double value = (Double) QLExpressUtil.execute(ruleCtx.getExpress(), fieldContext);
                    ruleCtx.setExpressValue(value);
                } catch (Exception e) {
                    log.error("规则表达式执行异常", e);
                }
            }
            ruleCtx.setHit(true);
            policyContext.addHitRule(ruleCtx.getPolicyCode(), ruleCtx);
            if (ruleCtx.getRuleTrue() == null) {
                return;
            }
            tagService.addTag(ruleCtx.getRuleTrue().getTagCodes());
            listDataService.addListData(ruleCtx.getRuleTrue().getAddList());
            fieldService.setField(ruleCtx.getRuleTrue().getSetField());
            smsTemplateService.sendSms(ruleCtx.getRuleTrue().getSendSms());
        } else {
            if (ruleCtx.getRuleFalse() == null) {
                return;
            }
            tagService.addTag(ruleCtx.getRuleFalse().getTagCodes());
            listDataService.addListData(ruleCtx.getRuleFalse().getAddList());
            fieldService.setField(ruleCtx.getRuleFalse().getSetField());
            smsTemplateService.sendSms(ruleCtx.getRuleFalse().getSendSms());
        }
    }
}
