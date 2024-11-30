package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.entity.PolicyVersionExt;
import cn.wnhyang.coolGuard.mapper.PolicyVersionExtMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyVersionExtService;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionExtPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 策略版本扩展表 服务实现类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyVersionExtServiceImpl implements PolicyVersionExtService {

    private final PolicyVersionExtMapper policyVersionExtMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        policyVersionExtMapper.deleteById(id);
    }

    @Override
    public PolicyVersionExt get(Long id) {
        return policyVersionExtMapper.selectById(id);
    }

    @Override
    public PageResult<PolicyVersionExt> page(PolicyVersionExtPageVO pageVO) {
        return policyVersionExtMapper.selectPage(pageVO);
    }

}
