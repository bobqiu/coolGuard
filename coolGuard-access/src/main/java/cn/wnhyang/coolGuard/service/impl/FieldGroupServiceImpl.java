package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.convert.FieldGroupConvert;
import cn.wnhyang.coolGuard.entity.FieldGroup;
import cn.wnhyang.coolGuard.mapper.FieldGroupMapper;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldGroupService;
import cn.wnhyang.coolGuard.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
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
    public Long createFieldGroup(FieldGroupCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        FieldGroup fieldGroup = FieldGroupConvert.INSTANCE.convert(createVO);
        fieldGroupMapper.insert(fieldGroup);
        return fieldGroup.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFieldGroup(FieldGroupUpdateVO updateVO) {
        validateForUpdate(updateVO.getId());
        FieldGroup fieldGroup = FieldGroupConvert.INSTANCE.convert(updateVO);
        fieldGroupMapper.updateById(fieldGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFieldGroup(Long id) {
        validateForDelete(id);
        fieldGroupMapper.deleteById(id);
    }

    @Override
    public FieldGroupVO getFieldGroup(Long id) {
        FieldGroup fieldGroup = fieldGroupMapper.selectById(id);
        FieldGroupVO fieldGroupVO = FieldGroupConvert.INSTANCE.convert(fieldGroup);
        if (fieldGroupVO != null) {
            fieldGroupVO.setCount(fieldMapper.selectCountByFieldGroupName(fieldGroup.getName()));
        }
        return fieldGroupVO;
    }

    @Override
    public PageResult<FieldGroupVO> pageFieldGroup(FieldGroupPageVO pageVO) {
        PageResult<FieldGroup> pageResult = fieldGroupMapper.selectPage(pageVO);
        PageResult<FieldGroupVO> convert = FieldGroupConvert.INSTANCE.convert(pageResult);
        convert.getList().forEach(fieldGroup -> fieldGroup.setCount(fieldMapper.selectCountByFieldGroupName(fieldGroup.getName())));
        return convert;
    }

    private void validateForDelete(Long id) {
        validateForUpdate(id);
        Long count = fieldMapper.selectCountByFieldGroupName(fieldGroupMapper.selectById(id).getName());
        if (count > 0) {
            throw exception(FIELD_GROUP_HAS_FIELD);
        }
    }

    private void validateForUpdate(Long id) {
        FieldGroup fieldGroup = fieldGroupMapper.selectById(id);
        if (fieldGroup == null) {
            throw exception(FIELD_GROUP_NOT_EXIST);
        }
        // 标准，不允许删除
        if (fieldGroup.getStandard()) {
            throw exception(FIELD_GROUP_STANDARD);
        }
    }

    private void validateForCreateOrUpdate(Long id, String name) {
        // 校验存在
        validateExists(id);
        // 校验名唯一
        validateNameUnique(id, name);
    }

    private void validateExists(Long id) {
        if (id == null) {
            return;
        }
        FieldGroup fieldGroup = fieldGroupMapper.selectById(id);
        if (fieldGroup == null) {
            throw exception(FIELD_GROUP_NOT_EXIST);
        }
    }

    private void validateNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        FieldGroup fieldGroup = fieldGroupMapper.selectByName(name);
        if (fieldGroup == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(FIELD_GROUP_NAME_EXIST);
        }
        if (!fieldGroup.getId().equals(id)) {
            throw exception(FIELD_GROUP_NAME_EXIST);
        }
    }

}
