package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.ConfigField;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.AccessVO;
import cn.wnhyang.coolGuard.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.vo.update.AccessUpdateVO;

import java.util.List;

/**
 * 接入表 服务类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
public interface AccessService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createAccess(AccessCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateAccess(AccessUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteAccess(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    AccessVO getAccess(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<AccessVO> pageAccess(AccessPageVO pageVO);

    /**
     * 根据名称查询
     *
     * @param name 名称
     * @return po
     */
    Access getAccessByName(String name);


    List<ConfigField> getInputFieldList(Long id);

    List<ConfigField> getOutputFieldList(Long id);

    /**
     * 根据服务id查询
     *
     * @param id 服务id
     * @return inputFieldVO集合
     */
    List<InputFieldVO> getAccessInputFieldList(Long id);

    /**
     * 根据服务id查询
     *
     * @param id 服务id
     * @return outputFieldVO集合
     */
    List<OutputFieldVO> getAccessOutputFieldList(Long id);
}
