package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Application;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolGuard.vo.page.ApplicationPageVO;
import cn.wnhyang.coolGuard.vo.update.ApplicationUpdateVO;

import java.util.List;

/**
 * 应用表 服务类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
public interface ApplicationService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createApplication(ApplicationCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateApplication(ApplicationUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteApplication(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Application getApplication(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Application> pageApplication(ApplicationPageVO pageVO);

    /**
     * 获取lv列表
     *
     * @return lv列表
     */
    List<LabelValue> getLabelValueList();
}
