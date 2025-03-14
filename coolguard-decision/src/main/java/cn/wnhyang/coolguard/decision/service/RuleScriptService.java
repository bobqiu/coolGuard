package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.RuleScript;
import cn.wnhyang.coolguard.decision.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolguard.decision.vo.update.RuleScriptUpdateVO;

/**
 * 规则脚本表 服务类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
public interface RuleScriptService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createRuleScript(RuleScriptCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateRuleScript(RuleScriptUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteRuleScript(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    RuleScript getRuleScript(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<RuleScript> pageRuleScript(RuleScriptPageVO pageVO);

}
