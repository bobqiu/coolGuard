package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.convert.ServiceConfigConvert;
import cn.wnhyang.coolGuard.entity.ConfigField;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.mapper.ServiceConfigMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ServiceConfigService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.ServiceConfigVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigCreateVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigPageVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigUpdateVO;
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

import static cn.wnhyang.coolGuard.exception.ErrorCodes.SERVICE_CONFIG_NAME_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.SERVICE_CONFIG_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 服务配置表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class ServiceConfigServiceImpl implements ServiceConfigService {

    private final ServiceConfigMapper serviceConfigMapper;

    private final ObjectMapper objectMapper;

    private final FieldMapper fieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createServiceConfig(ServiceConfigCreateVO createVO) {
        // 1、校验服务name唯一性
        validateForCreateOrUpdate(null, createVO.getName());
        ServiceConfig serviceConfig = ServiceConfigConvert.INSTANCE.convert(createVO);
        serviceConfigMapper.insert(serviceConfig);
        return serviceConfig.getId();
    }

    @Override
    public void updateServiceConfig(ServiceConfigUpdateVO updateVO) {
        ServiceConfig serviceConfig = ServiceConfigConvert.INSTANCE.convert(updateVO);

        serviceConfigMapper.updateById(serviceConfig);
    }

    @Override
    public void deleteServiceConfig(Long id) {
        validateExists(id);
        serviceConfigMapper.deleteById(id);
    }

    @Override
    public ServiceConfigVO getServiceConfig(Long id) {
        ServiceConfig serviceConfig = serviceConfigMapper.selectById(id);
        return ServiceConfigConvert.INSTANCE.convert(serviceConfig);
    }

    @Override
    public PageResult<ServiceConfigVO> pageServiceConfig(ServiceConfigPageVO pageVO) {
        PageResult<ServiceConfig> pageResult = serviceConfigMapper.selectPage(pageVO);
        return ServiceConfigConvert.INSTANCE.convert(pageResult);
    }

    @Override
    public ServiceConfig getServiceConfigByName(String name) {
        ServiceConfig serviceConfig = serviceConfigMapper.selectByName(name);
        if (serviceConfig == null) {
            throw exception(SERVICE_CONFIG_NOT_EXIST);
        }
        return serviceConfig;
    }

    @Override
    public List<ConfigField> getInputFieldList(Long id) {
        String json = serviceConfigMapper.selectInputConfig(id);
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));
        } catch (JsonProcessingException e) {
            log.error("inputConfig转换失败", e);
        }
        return List.of();
    }

    @Override
    public List<ConfigField> getOutputFieldList(Long id) {
        String json = serviceConfigMapper.selectOutputConfig(id);
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));
        } catch (JsonProcessingException e) {
            log.error("outputConfig转换失败", e);
        }
        return List.of();
    }

    @Override
    public List<InputFieldVO> getServiceConfigInputFieldList(Long id) {
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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "serviceIn", nodeType = NodeTypeEnum.COMMON)
    public void serviceIn(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        // 处理入参
        Map<String, String> params = decisionRequest.getParams();
        log.info("入参：{}", params);

    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "serviceOut", nodeType = NodeTypeEnum.COMMON)
    public void serviceOut(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        DecisionResponse decisionResponse = bindCmp.getContextBean(DecisionResponse.class);
        // 设置出参
        decisionResponse.setOutputData(FieldName.seqId, decisionRequest.getStringData(FieldName.seqId));

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
        ServiceConfig serviceConfig = serviceConfigMapper.selectById(id);
        if (serviceConfig == null) {
            throw exception(SERVICE_CONFIG_NOT_EXIST);
        }
    }

    private void validateNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        ServiceConfig serviceConfig = serviceConfigMapper.selectByName(name);
        if (serviceConfig == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(SERVICE_CONFIG_NAME_EXIST);
        }
        if (!serviceConfig.getId().equals(id)) {
            throw exception(SERVICE_CONFIG_NAME_EXIST);
        }
    }
}
