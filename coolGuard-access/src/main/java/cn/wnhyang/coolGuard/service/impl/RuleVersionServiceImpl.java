package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleVersionService;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
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

}
