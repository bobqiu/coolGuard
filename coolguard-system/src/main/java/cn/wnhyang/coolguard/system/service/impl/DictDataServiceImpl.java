package cn.wnhyang.coolguard.system.service.impl;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.convert.DictDataConvert;
import cn.wnhyang.coolguard.system.entity.DictDataDO;
import cn.wnhyang.coolguard.system.entity.DictTypeDO;
import cn.wnhyang.coolguard.system.mapper.DictDataMapper;
import cn.wnhyang.coolguard.system.mapper.DictTypeMapper;
import cn.wnhyang.coolguard.system.service.DictDataService;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataCreateVO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataPageVO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.system.error.SystemErrorCode.*;


/**
 * 字典数据
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl implements DictDataService {

    private final DictDataMapper dictDataMapper;

    private final DictTypeMapper dictTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictData(DictDataCreateVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(null, reqVO.getCode(), reqVO.getTypeCode());

        // 插入字典类型
        DictDataDO dictData = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictData(DictDataUpdateVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(reqVO.getId(), reqVO.getCode(), reqVO.getTypeCode());

        // 更新字典类型
        DictDataDO dictData = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.updateById(dictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(Long id) {
        // 校验是否存在
        validateDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public List<DictDataDO> getDictDataList() {
        return dictDataMapper.selectList();
    }

    @Override
    public PageResult<DictDataDO> getDictDataPage(DictDataPageVO reqVO) {
        return dictDataMapper.selectPage(reqVO);
    }

    @Override
    public DictDataDO getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public DictDataDO getDictData(String dictType, String code) {
        return dictDataMapper.selectByTypeCodeAndCode(dictType, code);
    }

    @Override
    public List<DictDataDO> getDictDataListByDictType(String type) {
        return dictDataMapper.selectListByDictTypeCode(type);
    }

    private void validateDictDataForCreateOrUpdate(Long id, String code, String dictType) {
        // 校验自己存在
        validateDictDataExists(id);
        // 校验字典类型有效
        validateDictTypeExists(dictType);
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(id, dictType, code);
    }

    public void validateDictDataValueUnique(Long id, String dictType, String code) {
        DictDataDO dictData = dictDataMapper.selectByTypeCodeAndCode(dictType, code);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    public void validateDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    public void validateDictTypeExists(String typeCode) {
        DictTypeDO dictType = dictTypeMapper.selectByCode(typeCode);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
    }
}
