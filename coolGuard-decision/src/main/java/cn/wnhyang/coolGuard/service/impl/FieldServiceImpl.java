package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.analysis.geo.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.ip.Ip2Region;
import cn.wnhyang.coolGuard.analysis.ip.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfo;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.ValueType;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.util.AdocUtil;
import cn.wnhyang.coolGuard.util.GeoHash;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.util.QLExpressUtil;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.vo.create.TestDynamicFieldScript;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 字段表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldMapper fieldMapper;

    private final PhoneNoAnalysis phoneNoAnalysis;

    private final IpAnalysis ipAnalysis;

    private final GeoAnalysis geoAnalysis;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public Long createField(FieldCreateVO createVO) {
        String prefix = createVO.getDynamic() ? "D_" : "N_";
        String fieldName = prefix + createVO.getType() + "_" + createVO.getTmpCode();
        if (fieldMapper.selectByCode(fieldName) != null) {
            throw exception(FIELD_NAME_EXIST);
        }
        Field field = FieldConvert.INSTANCE.convert(createVO);
        field.setCode(fieldName);
        fieldMapper.insert(field);
        return field.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public void updateField(FieldUpdateVO updateVO) {
        Field field = fieldMapper.selectById(updateVO.getId());
        if (field == null) {
            throw exception(FIELD_NOT_EXIST);
        }
        // 标准，不允许删除
        if (field.getStandard()) {
            throw exception(FIELD_STANDARD);
        }
        Field convert = FieldConvert.INSTANCE.convert(updateVO);
        fieldMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public void deleteField(Long id) {
        Field field = fieldMapper.selectById(id);
        if (field == null) {
            throw exception(FIELD_NOT_EXIST);
        }
        // 标准，不允许删除
        if (field.getStandard()) {
            throw exception(FIELD_STANDARD);
        }
        // TODO 查找引用 指标、接入、动态字段、规则等
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

    @Override
    public String testDynamicFieldScript(TestDynamicFieldScript testDynamicFieldScript) {
        FieldContext fieldContext = new FieldContext();
        Map<String, Object> params = testDynamicFieldScript.getParams();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Field field = fieldMapper.selectByCode(entry.getKey());
            FieldType byType = FieldType.getByType(field.getType());
            if (byType != null) {
                fieldContext.setDataByType(field.getCode(), (String) entry.getValue(), byType);
            }
        }

        try {
            Object execute = QLExpressUtil.execute(testDynamicFieldScript.getScript(), fieldContext);
            return String.valueOf(execute);
        } catch (Exception e) {
            // TODO 定义异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Field> listField() {
        return fieldMapper.selectList();
    }

    @Override
    public FieldContext fieldParse(List<InputFieldVO> inputFieldList, Map<String, String> params) {
        FieldContext fieldContext = new FieldContext();
        normalFieldParse(inputFieldList, params, fieldContext);
        dynamicFieldParse(inputFieldList, fieldContext);
        return fieldContext;
    }

    private void normalFieldParse(List<InputFieldVO> inputFieldList, Map<String, String> params, FieldContext fieldContext) {
        // 设置唯一id
        fieldContext.setDataByType(FieldName.seqId, IdUtil.fastSimpleUUID(), FieldType.STRING);

        // 普通字段处理
        inputFieldList.stream().filter(inputField -> !inputField.getDynamic()).forEach(inputField -> {
            Boolean required = inputField.getRequired();
            // 先处理普通字段
            String value = params.get(inputField.getParamName());
            // 如果必须，但输入为空则抛异常
            if (required && value == null) {
                throw new ServiceException(400, "参数[" + inputField.getParamName() + "]不能为空");
            }
            // 非必需，但为空，继续
            if (!required && value == null) {
                return;
            }
            try {
                FieldType fieldType = FieldType.getByType(inputField.getType());
                if (fieldType != null) {
                    fieldContext.setDataByType(inputField.getCode(), value, fieldType);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        LocalDateTime eventTime = fieldContext.getDateData(FieldName.eventTime);
        // 内置扩展字段
        fieldContext.setDataByType(FieldName.eventTimeStamp, String.valueOf(LocalDateTimeUtil.toEpochMilli(eventTime)), FieldType.STRING);


        // 身份证解析
        String idCard = fieldContext.getStringData(FieldName.payerIDNumber);
        if (IdcardUtil.isValidCard(idCard)) {
            Pca pca = AdocUtil.getPca(IdcardUtil.getDistrictCodeByIdCard(idCard));
            if (pca != null) {
                fieldContext.setDataByType(FieldName.idCardProvince, pca.getProvince(), FieldType.STRING);
                fieldContext.setDataByType(FieldName.idCardCity, pca.getCity(), FieldType.STRING);
                fieldContext.setDataByType(FieldName.idCardDistrict, pca.getArea(), FieldType.STRING);
            }
        }

        // 手机号解析
        String phoneNumber = fieldContext.getStringData(FieldName.payerPhoneNumber);
        PhoneNoInfo phoneNoInfo = phoneNoAnalysis.analysis(phoneNumber);
        fieldContext.setDataByType(FieldName.phoneNumberProvince, phoneNoInfo.getProvince(), FieldType.STRING);
        fieldContext.setDataByType(FieldName.phoneNumberCity, phoneNoInfo.getCity(), FieldType.STRING);
        fieldContext.setDataByType(FieldName.phoneNumberIsp, phoneNoInfo.getIsp(), FieldType.STRING);

        // ip解析
        String ip = fieldContext.getStringData(FieldName.ip);
        Ip2Region ip2Region = ipAnalysis.analysis(ip);
        if (ip2Region != null) {
            fieldContext.setDataByType(FieldName.ipCountry, ip2Region.getCountry(), FieldType.STRING);
            fieldContext.setDataByType(FieldName.ipProvince, ip2Region.getProvince(), FieldType.STRING);
            fieldContext.setDataByType(FieldName.ipCity, ip2Region.getCity(), FieldType.STRING);
            fieldContext.setDataByType(FieldName.ipIsp, ip2Region.getIsp(), FieldType.STRING);
        }

        // 经纬度解析
        String lonAndLat = fieldContext.getStringData(FieldName.lonAndLat);
        Pca pca = geoAnalysis.analysis(lonAndLat);
        if (pca != null) {
            fieldContext.setDataByType(FieldName.geoProvince, pca.getProvince(), FieldType.STRING);
            fieldContext.setDataByType(FieldName.geoCity, pca.getCity(), FieldType.STRING);
            fieldContext.setDataByType(FieldName.geoDistrict, pca.getArea(), FieldType.STRING);
        }

        // 经纬度geoHash编码
        fieldContext.setDataByType(FieldName.geoHash, GeoHash.geoHash(lonAndLat), FieldType.STRING);

    }

    private void dynamicFieldParse(List<InputFieldVO> inputFieldList, FieldContext fieldContext) {
        inputFieldList.stream().filter(InputFieldVO::getDynamic).forEach(inputField -> {
            try {
                Object result = QLExpressUtil.execute(inputField.getScript(), fieldContext);
                fieldContext.setDataByType(inputField.getCode(), String.valueOf(result), FieldType.getByType(inputField.getType()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.SET_FIELD, nodeType = NodeTypeEnum.COMMON, nodeName = "设置字段组件")
    public void setField(NodeComponent bindCmp) {
        // TODO 完善
        log.info("设置字段");
        List<Action.SetField> setFields = bindCmp.getCmpDataList(Action.SetField.class);
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        setFields.forEach(setField -> {
            String value = setField.getValue();
            if (ValueType.CONTEXT.equals(setField.getType())) {
                value = fieldContext.getStringData(value);
            }
            fieldContext.setDataByType(setField.getFieldName(), value, FieldType.STRING);
        });
    }

}
