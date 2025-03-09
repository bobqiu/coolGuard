package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.convert.PolicySetConvert;
import cn.wnhyang.coolGuard.decision.convert.RuleVersionConvert;
import cn.wnhyang.coolGuard.decision.entity.Rule;
import cn.wnhyang.coolGuard.decision.entity.RuleVersion;
import cn.wnhyang.coolGuard.decision.mapper.RuleMapper;
import cn.wnhyang.coolGuard.decision.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.decision.service.RuleVersionService;
import cn.wnhyang.coolGuard.decision.vo.RuleVersionVO;
import cn.wnhyang.coolGuard.decision.vo.page.RuleVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.RULE_NAME_EXIST;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.RULE_VERSION_NOT_EXIST;

/**
 * 规则版本表 服务实现类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleVersionServiceImpl implements RuleVersionService {

    private final RuleVersionMapper ruleVersionMapper;

    private final RuleMapper ruleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        RuleVersion ruleVersion = ruleVersionMapper.selectById(id);
        if (ruleVersion == null) {
            throw exception(RULE_VERSION_NOT_EXIST);
        }
        ruleVersionMapper.deleteById(id);
    }

    @Override
    public RuleVersion get(Long id) {
        return ruleVersionMapper.selectById(id);
    }

    @Override
    public RuleVersion getByCode(String code) {
        return ruleVersionMapper.selectByCode(code);
    }

    @Override
    public PageResult<RuleVersion> page(RuleVersionPageVO pageVO) {
        return ruleVersionMapper.selectPage(pageVO);
    }

    @Override
    public PageResult<RuleVersionVO> pageByCode(RuleVersionPageVO pageVO) {
        PageResult<RuleVersion> ruleVersionPageResult = ruleVersionMapper.selectPageByCode(pageVO);
        return RuleVersionConvert.INSTANCE.convert(ruleVersionPageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        RuleVersion ruleVersion = ruleVersionMapper.selectById(id);
        if (ruleVersion == null) {
            throw exception(RULE_VERSION_NOT_EXIST);
        }
        ruleMapper.updateByCode(new Rule().setCode(ruleVersion.getCode()).setPublish(Boolean.FALSE));
        ruleVersionMapper.updateById(new RuleVersion().setId(id).setLatest(Boolean.FALSE));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chose(Long id) {
        RuleVersion ruleVersion = ruleVersionMapper.selectById(id);
        if (ruleVersion == null) {
            throw exception(RULE_VERSION_NOT_EXIST);
        }
        Rule rule = ruleMapper.selectByCode(ruleVersion.getCode());
        Rule byName = ruleMapper.selectByName(ruleVersion.getName());
        if (byName != null && !rule.getId().equals(byName.getId())) {
            throw exception(RULE_NAME_EXIST);
        }
        Rule convert = PolicySetConvert.INSTANCE.convert(ruleVersion);
        convert.setPublish(Boolean.FALSE);
        ruleMapper.updateByCode(convert);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(ruleVersionMapper.selectList(), RuleVersion::getLabelValue);
    }

}
