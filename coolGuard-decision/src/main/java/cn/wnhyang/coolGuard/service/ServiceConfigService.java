package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.ConfigField;
import cn.wnhyang.coolGuard.entity.ServiceConfig;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.ServiceConfigVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigCreateVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigPageVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigUpdateVO;

import java.util.List;

/**
 * 服务配置表 服务类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
public interface ServiceConfigService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createServiceConfig(ServiceConfigCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateServiceConfig(ServiceConfigUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteServiceConfig(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    ServiceConfigVO getServiceConfig(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<ServiceConfigVO> pageServiceConfig(ServiceConfigPageVO pageVO);

    /**
     * 根据名称查询
     *
     * @param name 名称
     * @return po
     */
    ServiceConfig getServiceConfigByName(String name);


    List<ConfigField> getInputFieldList(Long id);

    List<ConfigField> getOutputFieldList(Long id);

    /**
     * 根据服务id查询
     *
     * @param id 服务id
     * @return inputFieldVO集合
     */
    List<InputFieldVO> getServiceConfigInputFieldList(Long id);

    /**
     * 根据服务id查询
     *
     * @param id 服务id
     * @return outputFieldVO集合
     */
    List<OutputFieldVO> getServiceConfigOutputFieldList(Long id);
}
