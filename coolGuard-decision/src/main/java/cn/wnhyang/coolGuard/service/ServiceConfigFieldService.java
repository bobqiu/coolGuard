package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.vo.InputFieldVO;

import java.util.List;

/**
 * 服务配置字段表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface ServiceConfigFieldService {

    /**
     * 根据服务id查询
     *
     * @param serviceId 服务id
     * @return inputFieldVO集合
     */
    List<InputFieldVO> getServiceConfigInputFieldsByServiceId(Long serviceId);

}
