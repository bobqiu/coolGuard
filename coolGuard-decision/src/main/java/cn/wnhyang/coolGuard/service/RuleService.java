package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;

import java.util.Collection;
import java.util.List;

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
     * 批量删除，与单删除区别是，已知id存在
     * 另外主要用于策略删除时关联删除
     *
     * @param ids dis
     */
    void deleteRule(Collection<Long> ids);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    RuleVO getRule(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<RuleVO> pageRule(RulePageVO pageVO);

    /**
     * 根据策略code查询
     *
     * @param policyCode 策略code
     * @return poList
     */
    List<RuleVO> listByPolicyCode(String policyCode);

    /**
     * 提交
     *
     * @param id id
     */
    void submit(Long id);
}
