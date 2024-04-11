package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;

/**
 * 规则表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface RuleService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createRule(RuleCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateRule(RuleUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteRule(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Rule getRule(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Rule> pageRule(RulePageVO pageVO);

}
