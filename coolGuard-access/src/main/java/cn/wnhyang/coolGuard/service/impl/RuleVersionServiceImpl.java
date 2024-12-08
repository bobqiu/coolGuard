package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.RuleVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleVersionService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.RULE_VERSION_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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

    private final ChainMapper chainMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        RuleVersion ruleVersion = ruleVersionMapper.selectById(id);
        if (ruleVersion == null) {
            throw exception(RULE_VERSION_NOT_EXIST);
        }
        ruleVersionMapper.updateById(new RuleVersion().setId(id).setLatest(Boolean.FALSE));
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.RULE_CHAIN, ruleVersion.getCode()));
    }

}
