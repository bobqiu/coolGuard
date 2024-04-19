package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.entity.ServiceConfigField;
import cn.wnhyang.coolGuard.mapper.FieldMapper;
import cn.wnhyang.coolGuard.mapper.ServiceConfigFieldMapper;
import cn.wnhyang.coolGuard.service.ServiceConfigFieldService;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务配置字段表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Service
@RequiredArgsConstructor
public class ServiceConfigFieldServiceImpl implements ServiceConfigFieldService {

    private final ServiceConfigFieldMapper serviceConfigFieldMapper;

    private final FieldMapper fieldMapper;

    @Override
    public List<InputFieldVO> getServiceConfigInputFieldsByServiceId(Long serviceId) {
        List<ServiceConfigField> serviceConfigFields = serviceConfigFieldMapper.selectListByServiceConfigId(serviceId);

        List<InputFieldVO> inputFieldVOList = new ArrayList<>();
        for (ServiceConfigField serviceConfigField : serviceConfigFields) {
            Field field = fieldMapper.selectByName(serviceConfigField.getFieldName());
            if (field != null) {
                InputFieldVO inputFieldVO = FieldConvert.INSTANCE.convert2InputFieldVO(field);
                inputFieldVO.setParamName(serviceConfigField.getParamName());
                inputFieldVO.setRequired(serviceConfigField.getRequired());
                inputFieldVOList.add(inputFieldVO);
            }
        }
        return inputFieldVOList;
    }

}
