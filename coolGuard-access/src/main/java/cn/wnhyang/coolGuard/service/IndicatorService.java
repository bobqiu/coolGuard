package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorByPolicySetPageVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;

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
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Indicator> pageIndicatorByPolicySet(IndicatorByPolicySetPageVO pageVO);

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
    void commit(VersionSubmitVO submitVO);
}
