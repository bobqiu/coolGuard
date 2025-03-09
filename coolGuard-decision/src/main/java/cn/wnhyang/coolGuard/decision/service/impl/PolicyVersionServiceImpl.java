package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.convert.PolicyConvert;
import cn.wnhyang.coolGuard.decision.convert.PolicyVersionConvert;
import cn.wnhyang.coolGuard.decision.entity.Policy;
import cn.wnhyang.coolGuard.decision.entity.PolicyVersion;
import cn.wnhyang.coolGuard.decision.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.decision.mapper.PolicyVersionMapper;
import cn.wnhyang.coolGuard.decision.service.PolicyVersionService;
import cn.wnhyang.coolGuard.decision.vo.PolicyVersionVO;
import cn.wnhyang.coolGuard.decision.vo.page.PolicyVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.POLICY_NAME_EXIST;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.POLICY_VERSION_NOT_EXIST;

/**
 * 策略版本表 服务实现类
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyVersionServiceImpl implements PolicyVersionService {

    private final PolicyVersionMapper policyVersionMapper;

    private final PolicyMapper policyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PolicyVersion policyVersion = policyVersionMapper.selectById(id);
        if (policyVersion == null) {
            throw exception(POLICY_VERSION_NOT_EXIST);
        }
        policyVersionMapper.deleteById(id);
    }

    @Override
    public PolicyVersion get(Long id) {
        return policyVersionMapper.selectById(id);
    }

    @Override
    public PageResult<PolicyVersion> page(PolicyVersionPageVO pageVO) {
        return policyVersionMapper.selectPage(pageVO);
    }

    @Override
    public PageResult<PolicyVersionVO> pageByCode(PolicyVersionPageVO pageVO) {
        PageResult<PolicyVersion> policyVersionPageResult = policyVersionMapper.selectPageByCode(pageVO);
        return PolicyVersionConvert.INSTANCE.convert(policyVersionPageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        PolicyVersion policyVersion = policyVersionMapper.selectById(id);
        if (policyVersion == null) {
            throw exception(POLICY_VERSION_NOT_EXIST);
        }
        policyMapper.updateByCode(new Policy().setCode(policyVersion.getCode()).setPublish(Boolean.FALSE));
        policyVersionMapper.updateById(new PolicyVersion().setId(id).setLatest(Boolean.FALSE));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chose(Long id) {
        PolicyVersion policyVersion = policyVersionMapper.selectById(id);
        if (policyVersion == null) {
            throw exception(POLICY_VERSION_NOT_EXIST);
        }
        Policy policy = policyMapper.selectByCode(policyVersion.getCode());
        Policy byName = policyMapper.selectByName(policyVersion.getName());
        if (byName != null && !policy.getId().equals(byName.getId())) {
            throw exception(POLICY_NAME_EXIST);
        }
        Policy convert = PolicyConvert.INSTANCE.convert(policyVersion);
        convert.setPublish(Boolean.FALSE);
        policyMapper.updateByCode(convert);
    }

    @Override
    public PolicyVersion getByCode(String code) {
        return policyVersionMapper.selectByCode(code);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(policyVersionMapper.selectLatestList(), PolicyVersion::getLabelValue);
    }

}
