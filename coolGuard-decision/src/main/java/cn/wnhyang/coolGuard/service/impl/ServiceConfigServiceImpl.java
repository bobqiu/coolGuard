package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.convert.ServiceConfigConvert;
import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.entity.ServiceConfigField;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.mapper.ServiceConfigFieldMapper;
import cn.wnhyang.coolGuard.mapper.ServiceConfigMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ServiceConfigService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigCreateVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigPageVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
public class ServiceConfigServiceImpl implements ServiceConfigService {

    private final ServiceConfigMapper serviceConfigMapper;

    private final ServiceConfigFieldMapper serviceConfigFieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createServiceConfig(ServiceConfigCreateVO createVO) {
        // 1、校验服务name唯一性
        validateForCreateOrUpdate(null, createVO.getName());
        ServiceConfig serviceConfig = ServiceConfigConvert.INSTANCE.convert(createVO);
        serviceConfigMapper.insert(serviceConfig);
        if (CollUtil.isNotEmpty(createVO.getInputFields())) {
            serviceConfigFieldMapper.insertBatch(CollectionUtils.convertList(createVO.getInputFields(),
                    inputField -> new ServiceConfigField().setServiceConfigId(serviceConfig.getId())
                            .setParamName(inputField.getParamName()).setRequired(inputField.getRequired())
                            .setFieldName(inputField.getFieldName())));
        }

        return serviceConfig.getId();
    }

    @Override
    public void updateServiceConfig(ServiceConfigUpdateVO updateVO) {
        validateForCreateOrUpdate(updateVO.getId(), updateVO.getName());
        ServiceConfig serviceConfig = ServiceConfigConvert.INSTANCE.convert(updateVO);

        serviceConfigFieldMapper.deleteByServiceConfigId(updateVO.getId());
        if (CollectionUtil.isNotEmpty(updateVO.getInputFields())) {
            serviceConfigFieldMapper.insertBatch(CollectionUtils.convertList(updateVO.getInputFields(),
                    inputField -> new ServiceConfigField().setServiceConfigId(serviceConfig.getId())
                            .setParamName(inputField.getParamName()).setRequired(inputField.getRequired())
                            .setFieldName(inputField.getFieldName())));
        }
        serviceConfigMapper.updateById(serviceConfig);
    }

    @Override
    public void deleteServiceConfig(Long id) {
        validateExists(id);
        serviceConfigMapper.deleteById(id);
        serviceConfigFieldMapper.deleteByServiceConfigId(id);
    }

    @Override
    public ServiceConfig getServiceConfig(Long id) {
        return serviceConfigMapper.selectById(id);
    }

    @Override
    public PageResult<ServiceConfig> pageServiceConfig(ServiceConfigPageVO pageVO) {
        return serviceConfigMapper.selectPage(pageVO);
    }

    @Override
    public ServiceConfig getServiceConfigByName(String name) {
        ServiceConfig serviceConfig = serviceConfigMapper.selectByName(name);
        if (serviceConfig == null) {
            throw exception(SERVICE_CONFIG_NOT_EXIST);
        }
        return serviceConfig;
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "serviceConfigPre", nodeType = NodeTypeEnum.COMMON)
    public void serviceConfigPre(NodeComponent bindCmp) {
        log.info("serviceConfigPre");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "serviceConfigProcess", nodeType = NodeTypeEnum.COMMON)
    public void serviceConfigProcess(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        List<InputFieldVO> inputFields = decisionRequest.getInputFields();
        // 处理入参
        Map<String, String> params = decisionRequest.getParams();
        log.info("入参：{}", params);

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
        // TODO 动态字段处理
        inputFields.stream().filter(InputFieldVO::getDynamic).forEach(inputField -> {

        });

        // TODO 解析服务，如手机号、ip、gps等


        log.info("系统字段：{}", decisionRequest.getFields());
    }


    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "serviceConfigFinally", nodeType = NodeTypeEnum.COMMON)
    public void serviceConfigFinally(NodeComponent bindCmp) {
        log.info("serviceConfigFinally");
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
