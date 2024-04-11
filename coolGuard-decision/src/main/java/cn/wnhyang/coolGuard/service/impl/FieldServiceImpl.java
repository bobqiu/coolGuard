package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_NAME_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 字段表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldMapper fieldMapper;

    @Override
    public Long createField(FieldCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        Field field = FieldConvert.INSTANCE.convert(createVO);
        fieldMapper.insert(field);
        return field.getId();
    }

    @Override
    public void updateField(FieldUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getName());
        Field field = FieldConvert.INSTANCE.convert(updateVO);
        fieldMapper.updateById(field);
    }

    @Override
    public void deleteField(Long id) {
        validateExists(id);
        fieldMapper.deleteById(id);
    }

    @Override
    public Field getField(Long id) {
        return fieldMapper.selectById(id);
    }

    @Override
    public PageResult<Field> pageField(FieldPageVO pageVO) {
        return fieldMapper.selectPage(pageVO);
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
        Field field = fieldMapper.selectById(id);
        if (field == null) {
            throw exception(FIELD_NOT_EXIST);
        }
    }

    private void validateNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        Field field = fieldMapper.selectByName(name);
        if (field == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(FIELD_NAME_EXIST);
        }
        if (!field.getId().equals(id)) {
            throw exception(FIELD_NAME_EXIST);
        }
    }

}
