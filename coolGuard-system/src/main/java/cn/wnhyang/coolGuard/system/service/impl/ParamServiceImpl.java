package cn.wnhyang.coolGuard.system.service.impl;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.ParamConvert;
import cn.wnhyang.coolGuard.system.entity.Param;
import cn.wnhyang.coolGuard.system.mapper.ParamMapper;
import cn.wnhyang.coolGuard.system.service.ParamService;
import cn.wnhyang.coolGuard.system.vo.param.ParamCreateVO;
import cn.wnhyang.coolGuard.system.vo.param.ParamPageVO;
import cn.wnhyang.coolGuard.system.vo.param.ParamUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.enums.ErrorCodes.*;

/**
 * 参数表 服务实现类
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParamServiceImpl implements ParamService {

    private final ParamMapper paramMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ParamCreateVO createVO) {
        validateDictForCreateOrUpdate(null, createVO.getLabel(), createVO.getValue());
        Param param = ParamConvert.INSTANCE.convert(createVO);
        paramMapper.insert(param);
        return param.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParamUpdateVO updateVO) {
        validateDictForCreateOrUpdate(updateVO.getId(), updateVO.getLabel(), updateVO.getValue());
        Param param = ParamConvert.INSTANCE.convert(updateVO);
        paramMapper.updateById(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        validateParamExists(id);
        Param param = paramMapper.selectById(id);
        if (param == null) {
            throw exception(PARAM_NOT_EXISTS);
        }
        if (param.getStandard()) {
            throw exception(PARAM_STANDARD_CANNOT_DELETE);
        }
        paramMapper.deleteById(id);
    }

    @Override
    public Param get(Long id) {
        return paramMapper.selectById(id);
    }

    @Override
    public PageResult<Param> page(ParamPageVO pageVO) {
        return paramMapper.selectPage(pageVO);
    }

    private void validateDictForCreateOrUpdate(Long id, String label, String value) {
        // 校验用户存在
        validateParamExists(id);
        // 校验label唯一
        validateLabelUnique(id, label);
        // 校验value唯一
        validateValueUnique(id, value);
    }

    private void validateParamExists(Long id) {
        if (id == null) {
            return;
        }
        Param param = paramMapper.selectById(id);
        if (param == null) {
            throw exception(PARAM_NOT_EXISTS);
        }
    }

    private void validateLabelUnique(Long id, String label) {
        Param param = paramMapper.selectByLabel(label);
        if (param == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(PARAM_LABEL_EXISTS);
        }
        if (!param.getId().equals(id)) {
            throw exception(PARAM_LABEL_EXISTS);
        }
    }

    private void validateValueUnique(Long id, String value) {
        Param param = paramMapper.selectByValue(value);
        if (param == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(PARAM_VALUE_EXISTS);
        }
        if (!param.getId().equals(id)) {
            throw exception(PARAM_VALUE_EXISTS);
        }
    }

}
