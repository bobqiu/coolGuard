package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.IndicatorVersionPageVO;

/**
 * 指标表历史表 服务类
 *
 * @author wnhyang
 * @since 2024/11/21
 */
public interface IndicatorVersionService {

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    IndicatorVersion get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<IndicatorVersion> page(IndicatorVersionPageVO pageVO);

    /**
     * 下线
     *
     * @param id id
     */
    void offline(Long id);
}
