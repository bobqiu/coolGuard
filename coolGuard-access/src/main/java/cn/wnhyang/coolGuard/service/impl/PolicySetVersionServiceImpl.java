package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.PolicySetVersionConvert;
import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mapper.PolicySetVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionService;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 策略集版本表 服务实现类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicySetVersionServiceImpl implements PolicySetVersionService {

    private final PolicySetVersionMapper policySetVersionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PolicySetVersionCreateVO createVO) {
        PolicySetVersion policySetVersion = PolicySetVersionConvert.INSTANCE.convert(createVO);
        policySetVersionMapper.insert(policySetVersion);
        return policySetVersion.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PolicySetVersionUpdateVO updateVO) {
        PolicySetVersion policySetVersion = PolicySetVersionConvert.INSTANCE.convert(updateVO);
        policySetVersionMapper.updateById(policySetVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        policySetVersionMapper.deleteById(id);
    }

    @Override
    public PolicySetVersion get(Long id) {
        return policySetVersionMapper.selectById(id);
    }

    @Override
    public PageResult<PolicySetVersion> page(PolicySetVersionPageVO pageVO) {
        return policySetVersionMapper.selectPage(pageVO);
    }

}
