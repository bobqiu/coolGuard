package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.RuleCondition;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.RuleConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.RuleConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.RuleConditionUpdateVO;

/**
 * 规则条件表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface RuleConditionService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createRuleCondition(RuleConditionCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateRuleCondition(RuleConditionUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteRuleCondition(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    RuleCondition getRuleCondition(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<RuleCondition> pageRuleCondition(RuleConditionPageVO pageVO);

}
