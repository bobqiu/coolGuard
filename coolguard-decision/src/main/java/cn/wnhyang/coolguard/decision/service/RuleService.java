package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.context.PolicyContext;
import cn.wnhyang.coolguard.decision.vo.RuleVO;
import cn.wnhyang.coolguard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolguard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.RuleCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.RulePageVO;
import cn.wnhyang.coolguard.decision.vo.update.RuleUpdateVO;
import jakarta.validation.Valid;

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
     * 提交
     *
     * @param submitVO submitVO
     */
    VersionSubmitResultVO submit(VersionSubmitVO submitVO);

    /**
     * 根据策略code查询
     *
     * @param policyCode 策略code
     * @return poList
     */
    List<RuleVO> listByPolicyCode(String policyCode);

    /**
     * 获取标签值列表
     *
     * @return labelValueList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 规则执行
     *
     * @param ruleCtx ruleCtx
     */
    void executeRule(PolicyContext.RuleCtx ruleCtx);

    /**
     * 批量提交
     *
     * @param submitVOList 提交VO
     * @return list
     */
    List<VersionSubmitResultVO> batchSubmit(@Valid BatchVersionSubmit submitVOList);
}
