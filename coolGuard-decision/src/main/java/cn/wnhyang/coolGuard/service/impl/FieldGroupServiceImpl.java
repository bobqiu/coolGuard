package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.convert.FieldGroupConvert;
import cn.wnhyang.coolGuard.entity.FieldGroup;
import cn.wnhyang.coolGuard.mapper.FieldGroupMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldGroupService;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_GROUP_NAME_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_GROUP_NOT_EXIST;
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

    @Override
    public Long createFieldGroup(FieldGroupCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        FieldGroup fieldGroup = FieldGroupConvert.INSTANCE.convert(createVO);
        fieldGroupMapper.insert(fieldGroup);
        return fieldGroup.getId();
    }

    @Override
    public void updateFieldGroup(FieldGroupUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getName());
        FieldGroup fieldGroup = FieldGroupConvert.INSTANCE.convert(updateVO);
        fieldGroupMapper.updateById(fieldGroup);
    }

    @Override
    public void deleteFieldGroup(Long id) {
        validateExists(id);
        fieldGroupMapper.deleteById(id);
    }

    @Override
    public FieldGroup getFieldGroup(Long id) {
        return fieldGroupMapper.selectById(id);
    }

    @Override
    public PageResult<FieldGroup> pageFieldGroup(FieldGroupPageVO pageVO) {
        return fieldGroupMapper.selectPage(pageVO);
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
