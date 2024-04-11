package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;

import java.util.List;
import java.util.Map;

/**
 * 指标表 服务类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
public interface IndicatorService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createIndicator(IndicatorCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateIndicator(IndicatorUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteIndicator(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Indicator getIndicator(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Indicator> pageIndicator(IndicatorPageVO pageVO);

    void compute(List<Indicator> indicatorList, Map<String, String> eventDetail);

    double getResultById(Long id, Map<String, String> eventDetail);

    double getResult(Indicator indicator, Map<String, String> eventDetail);
}
