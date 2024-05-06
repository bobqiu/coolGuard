package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Condition;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.ConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.ConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.ConditionUpdateVO;

/**
 * 规则条件表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface ConditionService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createCondition(ConditionCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateCondition(ConditionUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteCondition(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Condition getCondition(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Condition> pageCondition(ConditionPageVO pageVO);

}
