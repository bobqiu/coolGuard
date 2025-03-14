package cn.wnhyang.coolguard.system.service.impl;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.system.convert.DictTypeConvert;
import cn.wnhyang.coolguard.system.entity.DictTypeDO;
import cn.wnhyang.coolguard.system.mapper.DictDataMapper;
import cn.wnhyang.coolguard.system.mapper.DictTypeMapper;
import cn.wnhyang.coolguard.system.service.DictTypeService;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeCreateVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypePageVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.system.error.SystemErrorCode.*;


/**
 * 字典类型
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Service
@RequiredArgsConstructor
public class DictTypeServiceImpl implements DictTypeService {

    private final DictTypeMapper dictTypeMapper;

    private final DictDataMapper dictDataMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictType(DictTypeCreateVO reqVO) {
        // 校验正确性
        validateDictForCreateOrUpdate(null, reqVO.getName(), reqVO.getCode());

        // 插入字典类型
        DictTypeDO dictType = DictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(DictTypeUpdateVO reqVO) {
        // 校验正确性
        validateDictForCreateOrUpdate(reqVO.getId(), reqVO.getName(), null);
        // 更新字典类型
        DictTypeDO dictType = DictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.updateById(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(Long id) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectById(id);
        if (dictTypeDO == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (dictTypeDO.getStandard()) {
            throw exception(DICT_TYPE_STANDARD_NOT_ALLOW_DELETE);
        }
        // 删除字典数据
        dictDataMapper.deleteByTypeCode(dictTypeDO.getCode());
        // 删除字典类型
        dictTypeMapper.deleteById(id);
    }

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageVO reqVO) {
        return dictTypeMapper.selectPage(reqVO);
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(dictTypeMapper.selectList(), DictTypeDO::getLabelValue);
    }

    private void validateDictForCreateOrUpdate(Long id, String name, String code) {
        // 校验用户存在
        validateDictExists(id);
        // 校验label唯一
        validateLabelUnique(id, name);
        // 校验value唯一
        validateValueUnique(id, code);
    }

    private void validateDictExists(Long id) {
        if (id == null) {
            return;
        }
        DictTypeDO dictTypeDO = dictTypeMapper.selectById(id);
        if (dictTypeDO == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
    }

    private void validateLabelUnique(Long id, String name) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByName(name);
        if (dictTypeDO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(DICT_TYPE_LABEL_EXISTS);
        }
        if (!dictTypeDO.getId().equals(id)) {
            throw exception(DICT_TYPE_LABEL_EXISTS);
        }
    }

    private void validateValueUnique(Long id, String code) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByCode(code);
        if (dictTypeDO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(DICT_TYPE_VALUE_EXISTS);
        }
        if (!dictTypeDO.getId().equals(id)) {
            throw exception(DICT_TYPE_VALUE_EXISTS);
        }
    }

}
