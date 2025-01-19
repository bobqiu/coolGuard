package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.convert.FieldRefConvert;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.entity.FieldRef;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.mapper.FieldRefMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldRefService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldRefPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldRefUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段引用 服务实现类
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldRefServiceImpl implements FieldRefService {

    private final FieldRefMapper fieldRefMapper;

    private final FieldMapper fieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(FieldRefCreateVO createVO) {
        FieldRef fieldRef = FieldRefConvert.INSTANCE.convert(createVO);
        fieldRefMapper.insert(fieldRef);
        return fieldRef.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FieldRefUpdateVO updateVO) {
        FieldRef fieldRef = FieldRefConvert.INSTANCE.convert(updateVO);
        fieldRefMapper.updateById(fieldRef);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        fieldRefMapper.deleteById(id);
    }

    @Override
    public FieldRef get(Long id) {
        return fieldRefMapper.selectById(id);
    }

    @Override
    public PageResult<FieldRef> page(FieldRefPageVO pageVO) {
        return fieldRefMapper.selectPage(pageVO);
    }

    @Override
    public void copyAccess(String sourceCode, String targetCode) {
        // 将所有源字段引用复制一份目标字段
        List<FieldRef> fieldRefList = fieldRefMapper.selectListByRef("access", sourceCode, null);
        fieldRefList.forEach(fieldRef -> {
            fieldRef.setRefBy(targetCode).setId(null);
        });
        fieldRefMapper.insertBatch(fieldRefList);
    }

    @Override
    public List<InputFieldVO> getAccessInputFieldList(Access access) {
        List<FieldRef> fieldRefList = fieldRefMapper.selectListByRef("access", access.getCode(), "input");

        List<InputFieldVO> inputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(fieldRefList)) {
            for (FieldRef fieldRef : fieldRefList) {
                Field field = fieldMapper.selectByCode(fieldRef.getFieldCode());
                if (field != null) {
                    InputFieldVO inputFieldVO = FieldConvert.INSTANCE.convert2InputFieldVO(field);
                    inputFieldVO.setParamName(fieldRef.getParamName());
                    inputFieldVO.setRequired(fieldRef.getRequired());
                    inputFieldVOList.add(inputFieldVO);
                }
            }
        }
        return inputFieldVOList;
    }

    @Override
    public List<OutputFieldVO> getAccessOutputFieldList(Access access) {
        List<FieldRef> fieldRefList = fieldRefMapper.selectListByRef("access", access.getCode(), "output");

        List<OutputFieldVO> outputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(fieldRefList)) {
            for (FieldRef fieldRef : fieldRefList) {
                Field field = fieldMapper.selectByCode(fieldRef.getFieldCode());
                if (field != null) {
                    outputFieldVOList.add(new OutputFieldVO()
                            .setCode(fieldRef.getFieldCode())
                            .setParamName(fieldRef.getParamName()));
                }
            }
        }
        return outputFieldVOList;
    }

}
