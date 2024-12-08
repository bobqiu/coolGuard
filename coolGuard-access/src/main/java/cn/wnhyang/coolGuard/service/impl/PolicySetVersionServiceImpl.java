package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.POLICY_SET_VERSION_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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

    private final ChainMapper chainMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PolicySetVersion policySetVersion = policySetVersionMapper.selectById(id);
        if (policySetVersion == null) {
            throw exception(POLICY_SET_VERSION_NOT_EXIST);
        }
        policySetVersionMapper.deleteById(id);
    }

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
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        PolicySetVersion policySetVersion = policySetVersionMapper.selectById(id);
        if (policySetVersion == null) {
            throw exception(POLICY_SET_VERSION_NOT_EXIST);
        }
        policySetVersionMapper.updateById(new PolicySetVersion().setId(id).setLatest(Boolean.FALSE));
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.POLICY_SET_CHAIN, policySetVersion.getCode()));
    }

}
