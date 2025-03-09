package cn.wnhyang.coolGuard.decision.service;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.context.IndicatorContext;
import cn.wnhyang.coolGuard.decision.vo.IndicatorVO;
import cn.wnhyang.coolGuard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolGuard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolGuard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.decision.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.IndicatorUpdateVO;

import java.util.List;

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
    IndicatorVO getIndicator(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<IndicatorVO> pageIndicator(IndicatorPageVO pageVO);

    /**
     * 查询列表
     *
     * @return voList
     */
    List<IndicatorVO> listIndicator();

    /**
     * 提交
     *
     * @param submitVO 提交VO
     */
    VersionSubmitResultVO submit(VersionSubmitVO submitVO);

    /**
     * 批量提交
     *
     * @param submitVO 提交VO
     */
    List<VersionSubmitResultVO> batchSubmit(BatchVersionSubmit submitVO);

    /**
     * 获取lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 计算指标
     */
    void indicatorCompute();

    /**
     * 计算指标
     *
     * @param indicatorCtx 指标
     */
    void indicatorCompute(IndicatorContext.IndicatorCtx indicatorCtx);
}
