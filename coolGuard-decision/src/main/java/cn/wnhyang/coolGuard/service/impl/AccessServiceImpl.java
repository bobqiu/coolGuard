package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.convert.AccessConvert;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.ConfigField;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.mapper.AccessMapper;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.AccessVO;
import cn.wnhyang.coolGuard.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.vo.update.AccessUpdateVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.ACCESS_NAME_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.ACCESS_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 接入表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final AccessMapper accessMapper;

    private final ObjectMapper objectMapper;

    private final FieldMapper fieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAccess(AccessCreateVO createVO) {
        // 1、校验服务name唯一性
        validateForCreateOrUpdate(null, createVO.getName());
        Access access = AccessConvert.INSTANCE.convert(createVO);
        accessMapper.insert(access);
        return access.getId();
    }

    @Override
    public void updateAccess(AccessUpdateVO updateVO) {
        Access access = AccessConvert.INSTANCE.convert(updateVO);

        accessMapper.updateById(access);
    }

    @Override
    public void deleteAccess(Long id) {
        validateExists(id);
        accessMapper.deleteById(id);
    }

    @Override
    public AccessVO getAccess(Long id) {
        Access access = accessMapper.selectById(id);
        return AccessConvert.INSTANCE.convert(access);
    }

    @Override
    public PageResult<AccessVO> pageAccess(AccessPageVO pageVO) {
        PageResult<Access> pageResult = accessMapper.selectPage(pageVO);
        return AccessConvert.INSTANCE.convert(pageResult);
    }

    @Override
    public Access getAccessByName(String name) {
        Access access = accessMapper.selectByName(name);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        return access;
    }

    @Override
    public List<ConfigField> getInputFieldList(Long id) {
        String json = accessMapper.selectInputConfig(id);
        try {
            if (StrUtil.isNotBlank(json)){
                return objectMapper.readValue(json,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));
            }
        } catch (JsonProcessingException e) {
            log.error("inputConfig转换失败", e);
        }
        return List.of();
    }

    @Override
    public List<ConfigField> getOutputFieldList(Long id) {
        String json = accessMapper.selectOutputConfig(id);
        try {
            if (StrUtil.isNotBlank(json)){
                return objectMapper.readValue(json,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));
            }
        } catch (JsonProcessingException e) {
            log.error("outputConfig转换失败", e);
        }
        return List.of();
    }

    @Override
    public List<InputFieldVO> getAccessInputFieldList(Long id) {
        List<ConfigField> configFieldList = getInputFieldList(id);

        List<InputFieldVO> inputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(configFieldList)) {
            for (ConfigField configField : configFieldList) {
                Field field = fieldMapper.selectByName(configField.getFieldName());
                if (field != null) {
                    InputFieldVO inputFieldVO = FieldConvert.INSTANCE.convert2InputFieldVO(field);
                    inputFieldVO.setParamName(configField.getParamName());
                    inputFieldVO.setRequired(configField.getRequired());
                    inputFieldVOList.add(inputFieldVO);
                }
            }
        }
        return inputFieldVOList;
    }

    @Override
    public List<OutputFieldVO> getAccessOutputFieldList(Long id) {
        List<ConfigField> configFieldList = getOutputFieldList(id);

        List<OutputFieldVO> outputFieldVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(configFieldList)) {
            for (ConfigField configField : configFieldList) {
                Field field = fieldMapper.selectByName(configField.getFieldName());
                if (field != null) {
                    outputFieldVOList.add(new OutputFieldVO()
                            .setName(configField.getFieldName())
                            .setParamName(configField.getParamName()));
                }
            }
        }
        return outputFieldVOList;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "accessIn", nodeType = NodeTypeEnum.COMMON)
    public void accessIn(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        // 处理入参
        Map<String, String> params = decisionRequest.getParams();
        log.info("入参：{}", params);

    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "accessOut", nodeType = NodeTypeEnum.COMMON)
    public void accessOut(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        DecisionResponse decisionResponse = bindCmp.getContextBean(DecisionResponse.class);
        // 设置出参
        decisionResponse.setOutputData(FieldName.seqId, decisionRequest.getStringData(FieldName.seqId));
        List<OutputFieldVO> outputFields = decisionRequest.getOutputFields();
        if (CollUtil.isNotEmpty(outputFields)) {
            for (OutputFieldVO outputField : outputFields) {
                decisionResponse.setOutputData(outputField.getParamName(),
                        decisionRequest.getStringData(outputField.getName()));
            }
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
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
    }

    private void validateNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        Access access = accessMapper.selectByName(name);
        if (access == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(ACCESS_NAME_EXIST);
        }
        if (!access.getId().equals(id)) {
            throw exception(ACCESS_NAME_EXIST);
        }
    }
}
