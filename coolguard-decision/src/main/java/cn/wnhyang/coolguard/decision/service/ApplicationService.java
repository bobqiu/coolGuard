package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Application;
import cn.wnhyang.coolguard.decision.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.ApplicationPageVO;
import cn.wnhyang.coolguard.decision.vo.update.ApplicationUpdateVO;

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
