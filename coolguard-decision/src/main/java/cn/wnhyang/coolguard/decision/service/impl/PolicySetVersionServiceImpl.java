package cn.wnhyang.coolguard.decision.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.decision.convert.PolicySetConvert;
import cn.wnhyang.coolguard.decision.convert.PolicySetVersionConvert;
import cn.wnhyang.coolguard.decision.entity.PolicySet;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.mapper.ChainMapper;
import cn.wnhyang.coolguard.decision.mapper.PolicySetMapper;
import cn.wnhyang.coolguard.decision.mapper.PolicySetVersionMapper;
import cn.wnhyang.coolguard.decision.service.PolicySetVersionService;
import cn.wnhyang.coolguard.decision.util.LFUtil;
import cn.wnhyang.coolguard.decision.vo.PolicySetVersionVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.POLICY_SET_NAME_EXIST;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.POLICY_SET_VERSION_NOT_EXIST;

/**
 * 策略集表版本 服务实现类
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicySetVersionServiceImpl implements PolicySetVersionService {

    private final PolicySetVersionMapper policySetVersionMapper;

    private final PolicySetMapper policySetMapper;

    private final ChainMapper chainMapper;

    @Override
    public PolicySetVersion get(Long id) {
        return policySetVersionMapper.selectById(id);
    }

    @Override
    public PolicySetVersion getByCode(String code) {
        return policySetVersionMapper.selectByCode(code);
    }

    @Override
    public PageResult<PolicySetVersion> page(PolicySetVersionPageVO pageVO) {
        return policySetVersionMapper.selectPage(pageVO);
    }

    @Override
    public PageResult<PolicySetVersionVO> pageByCode(PolicySetVersionPageVO pageVO) {
        PageResult<PolicySetVersion> policySetVersionPageResult = policySetVersionMapper.selectPageByCode(pageVO);
        return PolicySetVersionConvert.INSTANCE.convert(policySetVersionPageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        PolicySetVersion policySetVersion = policySetVersionMapper.selectById(id);
        if (policySetVersion == null) {
            throw exception(POLICY_SET_VERSION_NOT_EXIST);
        }
        policySetMapper.updateByCode(new PolicySet().setCode(policySetVersion.getCode()).setPublish(Boolean.FALSE));
        policySetVersionMapper.updateById(new PolicySetVersion().setId(id).setLatest(Boolean.FALSE));
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySetVersion.getCode()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chose(Long id) {
        PolicySetVersion policySetVersion = policySetVersionMapper.selectById(id);
        if (policySetVersion == null) {
            throw exception(POLICY_SET_VERSION_NOT_EXIST);
        }
        PolicySet policySet = policySetMapper.selectByCode(policySetVersion.getCode());
        PolicySet byName = policySetMapper.selectByName(policySetVersion.getName());
        if (byName != null && !policySet.getId().equals(byName.getId())) {
            throw exception(POLICY_SET_NAME_EXIST);
        }
        PolicySet convert = PolicySetConvert.INSTANCE.convert(policySetVersion);
        convert.setPublish(Boolean.FALSE);
        policySetMapper.updateByCode(convert);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(policySetVersionMapper.selectLatestList(), PolicySetVersion::getLabelValue);
    }

}
