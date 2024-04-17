package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
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

import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_NAME_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.FIELD_NOT_EXIST;
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

    @Override
    public String testDynamicFieldScript(TestDynamicFieldScript testDynamicFieldScript) {
        DecisionRequest decisionRequest = new DecisionRequest(null, null, null, null);
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
        decisionRequest.setDataByType("seqId", IdUtil.fastSimpleUUID(), FieldType.STRING);

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
