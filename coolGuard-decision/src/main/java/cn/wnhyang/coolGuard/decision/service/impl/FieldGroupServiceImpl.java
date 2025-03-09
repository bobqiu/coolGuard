package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.decision.convert.FieldGroupConvert;
import cn.wnhyang.coolGuard.decision.entity.FieldGroup;
import cn.wnhyang.coolGuard.decision.mapper.FieldGroupMapper;
import cn.wnhyang.coolGuard.decision.mapper.FieldMapper;
import cn.wnhyang.coolGuard.decision.service.FieldGroupService;
import cn.wnhyang.coolGuard.decision.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.decision.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.FieldGroupUpdateVO;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.*;

/**
 * 字段分组表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Service
@RequiredArgsConstructor
public class FieldGroupServiceImpl implements FieldGroupService {

    private final FieldGroupMapper fieldGroupMapper;

    private final FieldMapper fieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD_GROUP, allEntries = true)
    public Long createFieldGroup(FieldGroupCreateVO createVO) {
        if (fieldGroupMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(FIELD_GROUP_CODE_EXIST);
        }
        FieldGroup fieldGroup = FieldGroupConvert.INSTANCE.convert(createVO);
        fieldGroupMapper.insert(fieldGroup);
        return fieldGroup.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD_GROUP, allEntries = true)
    public void updateFieldGroup(FieldGroupUpdateVO updateVO) {
        FieldGroup fieldGroup = fieldGroupMapper.selectById(updateVO.getId());
        if (fieldGroup == null) {
            throw exception(FIELD_GROUP_NOT_EXIST);
        }
        // 标准，不允许删除
        if (fieldGroup.getStandard()) {
            throw exception(FIELD_GROUP_STANDARD);
        }
        FieldGroup convert = FieldGroupConvert.INSTANCE.convert(updateVO);
        fieldGroupMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD_GROUP, allEntries = true)
    public void deleteFieldGroup(Long id) {
        FieldGroup fieldGroup = fieldGroupMapper.selectById(id);
        if (fieldGroup == null) {
            throw exception(FIELD_GROUP_NOT_EXIST);
        }
        // 标准，不允许删除
        if (fieldGroup.getStandard()) {
            throw exception(FIELD_GROUP_STANDARD);
        }
        Long count = fieldMapper.selectCountByFieldGroupCode(fieldGroup.getCode());
        if (count > 0) {
            throw exception(FIELD_GROUP_HAS_FIELD);
        }
        fieldGroupMapper.deleteById(id);
    }

    @Override
    public FieldGroupVO getFieldGroup(Long id) {
        FieldGroup fieldGroup = fieldGroupMapper.selectById(id);
        FieldGroupVO fieldGroupVO = FieldGroupConvert.INSTANCE.convert(fieldGroup);
        if (fieldGroupVO != null) {
            fieldGroupVO.setCount(fieldMapper.selectCountByFieldGroupCode(fieldGroup.getCode()));
        }
        return fieldGroupVO;
    }

    @Override
    public PageResult<FieldGroupVO> pageFieldGroup(FieldGroupPageVO pageVO) {
        PageResult<FieldGroup> pageResult = fieldGroupMapper.selectPage(pageVO);
        PageResult<FieldGroupVO> convert = FieldGroupConvert.INSTANCE.convert(pageResult);
        convert.getList().forEach(fieldGroup -> fieldGroup.setCount(fieldMapper.selectCountByFieldGroupCode(fieldGroup.getCode())));
        return convert;
    }

    @Override
    public List<FieldGroupVO> listFieldGroup() {
        return FieldGroupConvert.INSTANCE.convert(fieldGroupMapper.selectList());
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(fieldGroupMapper.selectList(), FieldGroup::getLabelValue);
    }
}
