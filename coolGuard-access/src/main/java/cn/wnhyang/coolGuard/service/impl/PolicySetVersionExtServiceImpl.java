package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.PolicySetVersionExtConvert;
import cn.wnhyang.coolGuard.entity.PolicySetVersionExt;
import cn.wnhyang.coolGuard.mapper.PolicySetVersionExtMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionExtService;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionExtCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionExtPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionExtUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 策略集版本扩展表 服务实现类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicySetVersionExtServiceImpl implements PolicySetVersionExtService {

    private final PolicySetVersionExtMapper policySetVersionExtMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PolicySetVersionExtCreateVO createVO) {
        PolicySetVersionExt policySetVersionExt = PolicySetVersionExtConvert.INSTANCE.convert(createVO);
        policySetVersionExtMapper.insert(policySetVersionExt);
        return policySetVersionExt.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PolicySetVersionExtUpdateVO updateVO) {
        PolicySetVersionExt policySetVersionExt = PolicySetVersionExtConvert.INSTANCE.convert(updateVO);
        policySetVersionExtMapper.updateById(policySetVersionExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        policySetVersionExtMapper.deleteById(id);
    }

    @Override
    public PolicySetVersionExt get(Long id) {
        return policySetVersionExtMapper.selectById(id);
    }

    @Override
    public PageResult<PolicySetVersionExt> page(PolicySetVersionExtPageVO pageVO) {
        return policySetVersionExtMapper.selectPage(pageVO);
    }

}
