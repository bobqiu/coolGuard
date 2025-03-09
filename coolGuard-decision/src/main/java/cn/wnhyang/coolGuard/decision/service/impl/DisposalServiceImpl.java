package cn.wnhyang.coolGuard.decision.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.convert.DisposalConvert;
import cn.wnhyang.coolGuard.decision.entity.Disposal;
import cn.wnhyang.coolGuard.decision.entity.Rule;
import cn.wnhyang.coolGuard.decision.mapper.DisposalMapper;
import cn.wnhyang.coolGuard.decision.mapper.RuleMapper;
import cn.wnhyang.coolGuard.decision.service.DisposalService;
import cn.wnhyang.coolGuard.decision.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.DisposalPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.DisposalUpdateVO;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.*;

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
    @CacheEvict(value = RedisKey.DISPOSAL, allEntries = true)
    public Long createDisposal(DisposalCreateVO createVO) {
        if (disposalMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(DISPOSAL_CODE_EXIST);
        }
        if (disposalMapper.selectByCode(createVO.getName()) != null) {
            throw exception(DISPOSAL_NAME_EXIST);
        }
        Disposal disposal = DisposalConvert.INSTANCE.convert(createVO);
        disposalMapper.insert(disposal);
        return disposal.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.DISPOSAL, allEntries = true)
    public void updateDisposal(DisposalUpdateVO updateVO) {
        Disposal disposal = disposalMapper.selectById(updateVO.getId());
        if (disposal == null) {
            throw exception(DISPOSAL_NOT_EXIST);
        }
        // 标准，不允许删除
        if (disposal.getStandard()) {
            throw exception(DISPOSAL_STANDARD);
        }
        Disposal byName = disposalMapper.selectByName(updateVO.getName());
        if (byName != null && !disposal.getId().equals(byName.getId())) {
            throw exception(DISPOSAL_NAME_EXIST);
        }
        Disposal convert = DisposalConvert.INSTANCE.convert(updateVO);
        disposalMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.DISPOSAL, allEntries = true)
    public void deleteDisposal(Long id) {
        Disposal disposal = disposalMapper.selectById(id);
        if (disposal == null) {
            throw exception(DISPOSAL_NOT_EXIST);
        }
        // 标准，不允许删除
        if (disposal.getStandard()) {
            throw exception(DISPOSAL_STANDARD);
        }
        List<Rule> ruleList = ruleMapper.selectByDisposalCode(disposal.getCode());
        if (CollUtil.isNotEmpty(ruleList)) {
            throw exception(DISPOSAL_REFERENCE);
        }
        disposalMapper.deleteById(id);
    }

    @Override
    public Disposal getDisposal(Long id) {
        Disposal disposal = disposalMapper.selectById(id);
        if (disposal == null) {
            throw exception(DISPOSAL_NOT_EXIST);
        }
        return disposal;
    }

    @Override
    public PageResult<Disposal> pageDisposal(DisposalPageVO pageVO) {
        return disposalMapper.selectPage(pageVO);
    }

    @Override
    public List<Disposal> listDisposal() {
        return disposalMapper.selectList();
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(disposalMapper.selectList(), Disposal::getLabelValue);
    }

}
