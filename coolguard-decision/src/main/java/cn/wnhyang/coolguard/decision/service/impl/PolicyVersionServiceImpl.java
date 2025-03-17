package cn.wnhyang.coolguard.decision.service.impl;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.decision.convert.PolicyConvert;
import cn.wnhyang.coolguard.decision.convert.PolicyVersionConvert;
import cn.wnhyang.coolguard.decision.entity.Policy;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.mapper.PolicyMapper;
import cn.wnhyang.coolguard.decision.mapper.PolicyVersionMapper;
import cn.wnhyang.coolguard.decision.service.PolicyVersionService;
import cn.wnhyang.coolguard.decision.vo.PolicyVersionVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicyVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.POLICY_NAME_EXIST;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.POLICY_VERSION_NOT_EXIST;

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
    public List<PolicyVersion> getByCode(String code) {
        return policyVersionMapper.selectByCode(code);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(policyVersionMapper.selectLatestList(), PolicyVersion::getLabelValue);
    }

    @Override
    public PolicyVersionVO getByCv(CvQueryVO queryVO) {
        return PolicyVersionConvert.INSTANCE.convert(policyVersionMapper.selectByCv(queryVO));
    }

}
