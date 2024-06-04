package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.analysis.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.analysis.ip.Ip2Region;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfo;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.RouteStatus;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.util.AdocUtil;
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
import org.springframework.stereotype.Service;

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
    public Long createField(FieldCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getName());
        Field field = FieldConvert.INSTANCE.convert(createVO);
        fieldMapper.insert(field);
        return field.getId();
    }

    @Override
    public void updateField(FieldUpdateVO updateVO) {
        validateForUpdate(updateVO.getId());
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getName());
        Field field = FieldConvert.INSTANCE.convert(updateVO);
        fieldMapper.updateById(field);
    }

    @Override
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
        DecisionRequest decisionRequest = new DecisionRequest(RouteStatus.STRATEGY_SET_P, null, null, null, null, null);
        Map<String, String> params = testDynamicFieldScript.getParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Field field = fieldMapper.selectByName(entry.getKey());
            FieldType byType = FieldType.getByType(field.getType());
            if (byType != null) {
                decisionRequest.setDataByType(field.getName(), entry.getValue(), byType);
            }
        }

        String result = "脚本运行异常";
        try {
            LiteFlowNodeBuilder.createScriptNode().setId("testDynamicFieldScript")
                    .setName("测试动态脚本")
                    .setScript(testDynamicFieldScript.getScript())
                    .build();

            LiteFlowChainELBuilder.createChain().setChainId("testDynamicFieldScriptChain").setEL(
                    "THEN(testDynamicFieldScript)"
            ).build();

            LiteflowResponse response = flowExecutor.execute2Resp("testDynamicFieldScriptChain", null, decisionRequest);
            if (response.isSuccess()) {
                String fieldName = testDynamicFieldScript.getFieldName();
                FieldType byFieldName = FieldType.getByFieldName(fieldName);
                if (byFieldName != null) {
                    result = switch (byFieldName) {
                        case STRING -> decisionRequest.getStringData(fieldName);
                        case NUMBER -> decisionRequest.getNumberData(fieldName).toString();
                        case FLOAT -> decisionRequest.getFloatData(fieldName).toString();
                        case DATE -> decisionRequest.getDateData(fieldName).toString();
                        case ENUM -> decisionRequest.getEnumData(fieldName);
                        case BOOLEAN -> decisionRequest.getBooleanData(fieldName).toString();
                    };
                }
            }
        } catch (Exception e) {
            result = "脚本运行异常";
        }

        return result;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "normalFieldProcess", nodeType = NodeTypeEnum.COMMON)
    public void normalFieldProcess(NodeComponent bindCmp) {

        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        List<InputFieldVO> inputFields = decisionRequest.getInputFields();
        // 处理入参
        Map<String, String> params = decisionRequest.getParams();

        // 设置唯一id
        decisionRequest.setDataByType(FieldName.seqId, IdUtil.fastSimpleUUID(), FieldType.STRING);

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
                    decisionRequest.setDataByType(inputField.getName(), value, fieldType);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        // 身份证解析
        String idCard = decisionRequest.getStringData(FieldName.payerIDNumber);
        if (IdcardUtil.isValidCard(idCard)) {
            Pca pca = AdocUtil.getPca(IdcardUtil.getDistrictCodeByIdCard(idCard));
            if (pca != null) {
                decisionRequest.setDataByType(FieldName.idCardProvince, pca.getProvince(), FieldType.STRING);
                decisionRequest.setDataByType(FieldName.idCardCity, pca.getCity(), FieldType.STRING);
                decisionRequest.setDataByType(FieldName.idCardDistrict, pca.getArea(), FieldType.STRING);
            }
        }

        // 手机号解析
        String phoneNumber = decisionRequest.getStringData(FieldName.payerPhoneNumber);
        PhoneNoInfo phoneNoInfo = phoneNoAnalysis.analysis(phoneNumber);
        decisionRequest.setDataByType(FieldName.phoneNumberProvince, phoneNoInfo.getProvince(), FieldType.STRING);
        decisionRequest.setDataByType(FieldName.phoneNumberCity, phoneNoInfo.getCity(), FieldType.STRING);
        decisionRequest.setDataByType(FieldName.phoneNumberIsp, phoneNoInfo.getIsp(), FieldType.STRING);

        // ip解析
        String ip = decisionRequest.getStringData(FieldName.ip);
        Ip2Region ip2Region = ipAnalysis.analysis(ip);
        if (ip2Region != null) {
            decisionRequest.setDataByType(FieldName.ipCountry, ip2Region.getCountry(), FieldType.STRING);
            decisionRequest.setDataByType(FieldName.ipProvince, ip2Region.getProvince(), FieldType.STRING);
            decisionRequest.setDataByType(FieldName.ipCity, ip2Region.getCity(), FieldType.STRING);
            decisionRequest.setDataByType(FieldName.ipIsp, ip2Region.getIsp(), FieldType.STRING);
        }

        // 经纬度解析
        String lonAndLat = decisionRequest.getStringData(FieldName.lonAndLat);
        Pca pca = geoAnalysis.analysis(lonAndLat);
        if (pca != null) {
            decisionRequest.setDataByType(FieldName.geoProvince, pca.getProvince(), FieldType.STRING);
            decisionRequest.setDataByType(FieldName.geoCity, pca.getCity(), FieldType.STRING);
            decisionRequest.setDataByType(FieldName.geoDistrict, pca.getArea(), FieldType.STRING);
        }


    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "dynamicFieldProcess", nodeType = NodeTypeEnum.COMMON)
    public void dynamicFieldProcess(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        List<InputFieldVO> inputFields = decisionRequest.getInputFields();
        inputFields.stream().filter(InputFieldVO::getDynamic).forEach(inputField -> {
            try {
                bindCmp.invoke2Resp(inputField.getScript(), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        log.info("系统字段：{}", decisionRequest.getFields());
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
