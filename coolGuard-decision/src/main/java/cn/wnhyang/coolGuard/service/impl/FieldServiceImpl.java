package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.analysis.geo.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.idcard.IdCardAnalysis;
import cn.wnhyang.coolGuard.analysis.ip.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.ValueType;
import cn.wnhyang.coolGuard.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.entity.ParamConfig;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.util.QLExpressUtil;
import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.vo.create.TestDynamicFieldScript;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.*;
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

    private final IdCardAnalysis idCardAnalysis;

    private final PhoneNoAnalysis phoneNoAnalysis;

    private final IpAnalysis ipAnalysis;

    private final GeoAnalysis geoAnalysis;

    private final RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public Long createField(FieldCreateVO createVO) {
        String prefix = createVO.getDynamic() ? "D_" : "N_";
        String fieldCode = prefix + createVO.getType() + "_" + createVO.getTmpCode();
        if (fieldMapper.selectByCode(fieldCode) != null) {
            throw exception(FIELD_CODE_EXIST);
        }
        Field field = FieldConvert.INSTANCE.convert(createVO);
        field.setCode(fieldCode);
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
        Field field = fieldMapper.selectById(id);
        if (field == null) {
            throw exception(FIELD_NOT_EXIST);
        }
        return field;
    }

    @Override
    public PageResult<Field> pageField(FieldPageVO pageVO) {
        return fieldMapper.selectPage(pageVO);
    }

    @Override
    public String testDynamicFieldScript(TestDynamicFieldScript testDynamicFieldScript) {
        try {
            Object execute = QLExpressUtil.execute(testDynamicFieldScript.getScript(), testDynamicFieldScript.getContext());
            return String.valueOf(execute);
        } catch (Exception e) {
            // TODO 定义异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Field> listField() {
        RMap<String, Field> fieldMap = loadFieldMapIfNecessary();
        return new ArrayList<>(fieldMap.values());
    }

    @Override
    public Map<String, Field> getFields() {
        return loadFieldMapIfNecessary();
    }

    /**
     * 统一的缓存加载方法
     */
    private RMap<String, Field> loadFieldMapIfNecessary() {
        RMap<String, Field> fieldMap = redissonClient.getMap(RedisKey.FIELD + RedisKey.MAP);

        if (fieldMap.isEmpty()) {
            RLock lock = redissonClient.getLock(RedisKey.FIELD + RedisKey.MAP + RedisKey.LOCK);
            try {
                lock.lock();
                if (fieldMap.isEmpty()) {
                    List<Field> dbFields;
                    try {
                        dbFields = fieldMapper.selectList();
                    } catch (Exception e) {
                        log.error("Database query failed", e);
                        return fieldMap;
                    }

                    Map<String, Field> fieldData = dbFields.stream()
                            .collect(Collectors.toMap(Field::getCode, Function.identity()));

                    if (!fieldData.isEmpty()) {
                        fieldMap.putAll(fieldData);
                        fieldMap.expire(Duration.ofHours(6));
                    } else {
                        fieldMap.expire(Duration.ofMinutes(5));
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        return fieldMap;
    }

    @Override
    public FieldContext fieldParse(List<ParamConfig> inputFieldList, Map<String, String> params) {
        FieldContext fieldContext = new FieldContext();
        normalFieldParse(inputFieldList, params, fieldContext);
        dynamicFieldParse(inputFieldList, fieldContext);
        return fieldContext;
    }

    private void normalFieldParse(List<ParamConfig> inputFieldList, Map<String, String> params, FieldContext fieldContext) {
        // 设置唯一id
        fieldContext.setDataByType(FieldCode.SEQ_ID, IdUtil.fastSimpleUUID(), FieldType.STRING);

        // 普通字段处理
        inputFieldList.stream().filter(inputField -> !inputField.getDynamic()).forEach(inputField -> {
            Boolean required = inputField.getRequired();
            // 先处理普通字段
            String value = params.get(inputField.getParamName());
            // 如果必须，但输入为空则抛异常
            if (required && StrUtil.isBlank(value)) {
                throw new ServiceException(400, "参数[" + inputField.getParamName() + "]不能为空");
            }
            // 非必需，但为空，继续
            if (!required && StrUtil.isBlank(value)) {
                return;
            }
            try {
                FieldType fieldType = FieldType.getByType(inputField.getType());
                if (fieldType != null) {
                    fieldContext.setDataByType(inputField.getFieldCode(), value, fieldType);
                }

            } catch (Exception e) {
                throw new ServiceException(400, "参数[" + inputField.getParamName() + "]类型不合法");
            }
        });

        LocalDateTime eventTime = fieldContext.getData(FieldCode.EVENT_TIME, LocalDateTime.class);
        // 内置扩展字段
        fieldContext.setDataByType(FieldCode.EVENT_TIME_STAMP, String.valueOf(LocalDateTimeUtil.toEpochMilli(eventTime)), FieldType.STRING);


        // 身份证解析
        idCardAnalysis.parseIdCard(fieldContext);

        // 手机号解析
        phoneNoAnalysis.parsePhoneNumber(fieldContext);

        // ip解析
        ipAnalysis.parseIp(fieldContext);

        // 经纬度解析
        geoAnalysis.parseGeo(fieldContext);

    }

    private void dynamicFieldParse(List<ParamConfig> inputFieldList, FieldContext fieldContext) {
        inputFieldList.stream().filter(ParamConfig::getDynamic).forEach(inputField -> {
            try {
                Object result = QLExpressUtil.execute(inputField.getScript(), fieldContext);
                fieldContext.setDataByType(inputField.getFieldCode(), String.valueOf(result), FieldType.getByType(inputField.getType()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setField(List<Action.SetField> setFields) {
        if (CollUtil.isEmpty(setFields)) {
            return;
        }
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        // TODO 完善
        log.info("setField");
        setFields.forEach(setField -> {
            log.info("setField：{}", setField);
            String value = setField.getValue();
            if (ValueType.CONTEXT.equals(setField.getType())) {
                value = fieldContext.getData2String(value);
            }
            // TODO 根据value类型设置
            fieldContext.setDataByType(setField.getFieldCode(), value, FieldType.STRING);
        });
    }

}
