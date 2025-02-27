package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.convert.FieldGroupConvert;
import cn.wnhyang.coolGuard.entity.FieldGroup;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.mapper.FieldGroupMapper;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldGroupService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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
        if (fieldGroupMapper.selectByCode(createVO.getName()) != null) {
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
