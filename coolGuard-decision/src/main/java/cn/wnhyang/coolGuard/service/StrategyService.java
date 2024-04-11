package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Strategy;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.StrategyCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategyPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategyUpdateVO;

/**
 * 策略表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface StrategyService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createStrategy(StrategyCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateStrategy(StrategyUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteStrategy(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Strategy getStrategy(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Strategy> pageStrategy(StrategyPageVO pageVO);

}
