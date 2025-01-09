package cn.wnhyang.coolGuard.system.service.impl;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.DictConvert;
import cn.wnhyang.coolGuard.system.entity.Dict;
import cn.wnhyang.coolGuard.system.entity.DictData;
import cn.wnhyang.coolGuard.system.mapper.DictMapper;
import cn.wnhyang.coolGuard.system.service.DictService;
import cn.wnhyang.coolGuard.system.vo.dict.DictCreateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictPageVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictUpdateVO;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.enums.ErrorCodes.*;

/**
 * 字典表 服务实现类
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(DictCreateVO createVO) {
        validateDictForCreateOrUpdate(null, createVO.getLabel(), createVO.getValue());
        Dict dict = DictConvert.INSTANCE.convert(createVO);
        dictMapper.insert(dict);
        return dict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictUpdateVO updateVO) {
        validateDictForCreateOrUpdate(updateVO.getId(), updateVO.getLabel(), updateVO.getValue());
        Dict dict = DictConvert.INSTANCE.convert(updateVO);
        dictMapper.updateById(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Dict dict = dictMapper.selectById(id);
        if (dict == null) {
            throw exception(DICT_NOT_EXISTS);
        }
        if (dict.getStandard()) {
            throw exception(DICT_STANDARD_NOT_ALLOW_DELETE);
        }
        dictMapper.deleteById(id);
    }

    @Override
    public Dict get(Long id) {
        return dictMapper.selectById(id);
    }

    @Override
    public PageResult<Dict> page(DictPageVO pageVO) {
        return dictMapper.selectPage(pageVO);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(dictMapper.selectList(), Dict::getLabelValue);
    }

    @Override
    public List<DictData> getDataList(String value) {
        Dict dict = dictMapper.selectByValue(value);
        if (dict != null) {
            return dict.getData();
        }
        return List.of();
    }

    private void validateDictForCreateOrUpdate(Long id, String label, String value) {
        // 校验用户存在
        validateDictExists(id);
        // 校验label唯一
        validateLabelUnique(id, label);
        // 校验value唯一
        validateValueUnique(id, value);
    }

    private void validateDictExists(Long id) {
        if (id == null) {
            return;
        }
        Dict dict = dictMapper.selectById(id);
        if (dict == null) {
            throw exception(DICT_NOT_EXISTS);
        }
    }

    private void validateLabelUnique(Long id, String label) {
        Dict dict = dictMapper.selectByLabel(label);
        if (dict == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(DICT_LABEL_EXISTS);
        }
        if (!dict.getId().equals(id)) {
            throw exception(DICT_LABEL_EXISTS);
        }
    }

    private void validateValueUnique(Long id, String value) {
        Dict dict = dictMapper.selectByValue(value);
        if (dict == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(DICT_VALUE_EXISTS);
        }
        if (!dict.getId().equals(id)) {
            throw exception(DICT_VALUE_EXISTS);
        }
    }

}
