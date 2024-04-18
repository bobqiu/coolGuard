package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.ServiceConfigField;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigFieldPageVO;

import java.util.List;

/**
 * 服务配置字段表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface ServiceConfigFieldService {

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    ServiceConfigField getServiceConfigField(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<ServiceConfigField> pageServiceConfigField(ServiceConfigFieldPageVO pageVO);

    /**
     * 根据服务id查询
     *
     * @param serviceId 服务id
     * @return inputFieldVO集合
     */
    List<InputFieldVO> getServiceConfigInputFieldsByServiceId(Long serviceId);

}
