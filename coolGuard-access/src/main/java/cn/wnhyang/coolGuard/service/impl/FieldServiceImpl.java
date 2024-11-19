package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.analysis.geo.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.ip.Ip2Region;
import cn.wnhyang.coolGuard.analysis.ip.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfo;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.util.AdocUtil;
import cn.wnhyang.coolGuard.util.GeoHash;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.vo.create.TestDynamicFieldScript;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.builder.LiteFlowNodeBuilder;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final FlowExecutor flowExecutor;

    private final PhoneNoAnalysis phoneNoAnalysis;

    private final IpAnalysis ipAnalysis;

    private final GeoAnalysis geoAnalysis;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public Long createField(FieldCreateVO createVO) {
        // TODO 新增和修改要验证字段名命名规范
        validateForCreateOrUpdate(null, createVO.getName());
        Field field = FieldConvert.INSTANCE.convert(createVO);
        fieldMapper.insert(field);
        return field.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public void updateField(FieldUpdateVO updateVO) {
        validateForUpdate(updateVO.getId());
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getName());
        Field field = FieldConvert.INSTANCE.convert(updateVO);
        fieldMapper.updateById(field);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.FIELD, allEntries = true)
    public void deleteField(Long id) {
        validateForDelete(id);
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
        AccessRequest accessRequest = new AccessRequest(null, null, null, null);
        Map<String, String> params = testDynamicFieldScript.getParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Field field = fieldMapper.selectByName(entry.getKey());
            FieldType byType = FieldType.getByType(field.getType());
            if (byType != null) {
                accessRequest.setDataByType(field.getName(), entry.getValue(), byType);
            }
        }

        String result = "脚本运行异常";
        try {
            LiteFlowNodeBuilder.createScriptNode().setId("testDynamicFieldScript")
                    .setName("测试动态脚本")
                    .setScript(testDynamicFieldScript.getScript())
                    .build();

            LiteFlowChainELBuilder.createChain().setChainId(LFUtil.DYNAMIC_TEST_CHAIN).setEL(
                    "THEN(testDynamicFieldScript)"
            ).build();

            LiteflowResponse response = flowExecutor.execute2Resp(LFUtil.DYNAMIC_TEST_CHAIN, null, accessRequest);
            if (response.isSuccess()) {
                String fieldName = testDynamicFieldScript.getFieldName();
                FieldType byFieldName = FieldType.getByFieldName(fieldName);
                if (byFieldName != null) {
                    result = switch (byFieldName) {
                        case STRING -> accessRequest.getStringData(fieldName);
                        case NUMBER -> accessRequest.getNumberData(fieldName).toString();
                        case FLOAT -> accessRequest.getFloatData(fieldName).toString();
                        case DATE -> accessRequest.getDateData(fieldName).toString();
                        case ENUM -> accessRequest.getEnumData(fieldName);
                        case BOOLEAN -> accessRequest.getBooleanData(fieldName).toString();
                    };
                }
            }
        } catch (Exception e) {
            result = "脚本运行异常";
        }

        return result;
    }

    @Override
    public List<Field> listField() {
        return fieldMapper.selectList();
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.NORMAL_FIELD_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "普通字段组件")
    public void normalField(NodeComponent bindCmp) {

        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);

        List<InputFieldVO> inputFields = accessRequest.getInputFields();
        // 处理入参
        Map<String, String> params = accessRequest.getParams();

        // 设置唯一id
        accessRequest.setDataByType(FieldName.seqId, IdUtil.fastSimpleUUID(), FieldType.STRING);

        // 普通字段处理
        inputFields.stream().filter(inputField -> !inputField.getDynamic()).forEach(inputField -> {
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
                    accessRequest.setDataByType(inputField.getName(), value, fieldType);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        // 身份证解析
        String idCard = accessRequest.getStringData(FieldName.payerIDNumber);
        if (IdcardUtil.isValidCard(idCard)) {
            Pca pca = AdocUtil.getPca(IdcardUtil.getDistrictCodeByIdCard(idCard));
            if (pca != null) {
                accessRequest.setDataByType(FieldName.idCardProvince, pca.getProvince(), FieldType.STRING);
                accessRequest.setDataByType(FieldName.idCardCity, pca.getCity(), FieldType.STRING);
                accessRequest.setDataByType(FieldName.idCardDistrict, pca.getArea(), FieldType.STRING);
            }
        }

        // 手机号解析
        String phoneNumber = accessRequest.getStringData(FieldName.payerPhoneNumber);
        PhoneNoInfo phoneNoInfo = phoneNoAnalysis.analysis(phoneNumber);
        accessRequest.setDataByType(FieldName.phoneNumberProvince, phoneNoInfo.getProvince(), FieldType.STRING);
        accessRequest.setDataByType(FieldName.phoneNumberCity, phoneNoInfo.getCity(), FieldType.STRING);
        accessRequest.setDataByType(FieldName.phoneNumberIsp, phoneNoInfo.getIsp(), FieldType.STRING);

        // ip解析
        String ip = accessRequest.getStringData(FieldName.ip);
        Ip2Region ip2Region = ipAnalysis.analysis(ip);
        if (ip2Region != null) {
            accessRequest.setDataByType(FieldName.ipCountry, ip2Region.getCountry(), FieldType.STRING);
            accessRequest.setDataByType(FieldName.ipProvince, ip2Region.getProvince(), FieldType.STRING);
            accessRequest.setDataByType(FieldName.ipCity, ip2Region.getCity(), FieldType.STRING);
            accessRequest.setDataByType(FieldName.ipIsp, ip2Region.getIsp(), FieldType.STRING);
        }

        // 经纬度解析
        String lonAndLat = accessRequest.getStringData(FieldName.lonAndLat);
        Pca pca = geoAnalysis.analysis(lonAndLat);
        if (pca != null) {
            accessRequest.setDataByType(FieldName.geoProvince, pca.getProvince(), FieldType.STRING);
            accessRequest.setDataByType(FieldName.geoCity, pca.getCity(), FieldType.STRING);
            accessRequest.setDataByType(FieldName.geoDistrict, pca.getArea(), FieldType.STRING);
        }

        // 经纬度geoHash编码
        accessRequest.setDataByType(FieldName.geoHash, GeoHash.geoHash(lonAndLat), FieldType.STRING);

    }


    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.DYNAMIC_FIELD_COMMON_NODE, nodeType = NodeTypeEnum.COMMON, nodeName = "动态字段组件")
    public void dynamicField(NodeComponent bindCmp) {
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);

        List<InputFieldVO> inputFields = accessRequest.getInputFields();
        inputFields.stream().filter(InputFieldVO::getDynamic).forEach(inputField -> {
            try {
                bindCmp.invoke2Resp(inputField.getScript(), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        log.info("系统字段：{}", accessRequest.getFields());
    }

    private void validateForDelete(Long id) {
        validateForUpdate(id);
        // TODO 查找引用 指标、接入、动态字段、规则等
    }

    private void validateForUpdate(Long id) {
        Field field = fieldMapper.selectById(id);
        if (field == null) {
            throw exception(FIELD_NOT_EXIST);
        }
        // 标准，不允许删除
        if (field.getStandard()) {
            throw exception(FIELD_STANDARD);
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
