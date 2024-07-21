package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.convert.DisposalConvert;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.mapper.DisposalMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.DisposalService;
import cn.wnhyang.coolGuard.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.vo.page.DisposalPageVO;
import cn.wnhyang.coolGuard.vo.update.DisposalUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 处置表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Service
@RequiredArgsConstructor
public class DisposalServiceImpl implements DisposalService {

    private final DisposalMapper disposalMapper;

    private final RuleMapper ruleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RedisKey.DISPOSAL, allEntries = true)
    public Long createDisposal(DisposalCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Disposal disposal = DisposalConvert.INSTANCE.convert(createVO);
        disposalMapper.insert(disposal);
        return disposal.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RedisKey.DISPOSAL, allEntries = true)
    public void updateDisposal(DisposalUpdateVO updateVO) {
        validateForUpdate(updateVO.getId());
        Disposal disposal = DisposalConvert.INSTANCE.convert(updateVO);
        disposalMapper.updateById(disposal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RedisKey.DISPOSAL, allEntries = true)
    public void deleteDisposal(Long id) {
        validateForDelete(id);
        disposalMapper.deleteById(id);
    }

    @Override
    public Disposal getDisposal(Long id) {
        return disposalMapper.selectById(id);
    }

    @Override
    public PageResult<Disposal> pageDisposal(DisposalPageVO pageVO) {
        return disposalMapper.selectPage(pageVO);
    }

    @Override
    @Cacheable(cacheNames = RedisKey.DISPOSAL + "::list", unless = "#result == null")
    public List<Disposal> listDisposal() {
        return disposalMapper.selectList();
    }

    private void validateForDelete(Long id) {
        validateForUpdate(id);
        List<Rule> ruleList = ruleMapper.selectByDisposalId(id);
        if (CollUtil.isNotEmpty(ruleList)) {
            throw exception(DISPOSAL_REFERENCE);
        }
    }

    private void validateForUpdate(Long id) {
        Disposal disposal = disposalMapper.selectById(id);
        if (disposal == null) {
            throw exception(DISPOSAL_NOT_EXIST);
        }
        // 标准，不允许删除
        if (disposal.getStandard()) {
            throw exception(DISPOSAL_STANDARD);
        }
    }

    private void validateForCreateOrUpdate(Long id, String name) {
        // 校验存在
        validateExists(id);
        // 校验名唯一
        validateCodeUnique(id, name);
    }

    private void validateExists(Long id) {
        if (id == null) {
            return;
        }
        Disposal disposal = disposalMapper.selectById(id);
        if (disposal == null) {
            throw exception(DISPOSAL_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        Disposal disposal = disposalMapper.selectByCode(code);
        if (disposal == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(DISPOSAL_CODE_EXIST);
        }
        if (!disposal.getId().equals(id)) {
            throw exception(DISPOSAL_CODE_EXIST);
        }
    }

}
