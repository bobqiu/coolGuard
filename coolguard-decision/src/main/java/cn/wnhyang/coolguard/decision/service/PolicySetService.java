package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.vo.PolicySetVO;
import cn.wnhyang.coolguard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolguard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetPageVO;
import cn.wnhyang.coolguard.decision.vo.update.PolicySetUpdateVO;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;

/**
 * 策略集表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface PolicySetService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createPolicySet(PolicySetCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updatePolicySet(PolicySetUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deletePolicySet(Long id);

    /**
     * 批量删除，与单删除区别是，已知id存在
     *
     * @param ids id集合
     */
    void deletePolicySet(Collection<Long> ids);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    PolicySetVO getPolicySet(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVO> pagePolicySet0(PolicySetPageVO pageVO);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVO> pagePolicySet(PolicySetPageVO pageVO);

    /**
     * 提交
     *
     * @param submitVO submitVO
     */
    VersionSubmitResultVO submit(VersionSubmitVO submitVO);

    /**
     * 列表
     *
     * @return list
     */
    List<PolicySetVO> listPolicySet();

    /**
     * 获取标签值列表
     *
     * @return list
     */
    List<LabelValue> getLabelValueList();

    /**
     * 策略集
     */
    void policySet();

    /**
     * 批量提交
     *
     * @param submitVOList 提交VO
     * @return list
     */
    List<VersionSubmitResultVO> batchSubmit(@Valid BatchVersionSubmit submitVOList);
}
