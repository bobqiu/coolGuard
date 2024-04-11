package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.StrategySet;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.StrategySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategySetPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategySetUpdateVO;

/**
 * 策略集表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface StrategySetService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createStrategySet(StrategySetCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateStrategySet(StrategySetUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteStrategySet(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    StrategySet getStrategySet(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<StrategySet> pageStrategySet(StrategySetPageVO pageVO);

}
