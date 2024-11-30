package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.entity.PolicyVersion;
import cn.wnhyang.coolGuard.mapper.PolicyVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyVersionService;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 策略版本表 服务实现类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyVersionServiceImpl implements PolicyVersionService {

    private final PolicyVersionMapper policyVersionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
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

}
