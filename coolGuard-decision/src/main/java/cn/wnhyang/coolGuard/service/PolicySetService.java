package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetChainUpdateVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetUpdateVO;

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
    PageResult<PolicySetVO> pagePolicySet(PolicySetPageVO pageVO);

    /**
     * 更新策略集链
     *
     * @param updateVO 更新VO
     */
    void updatePolicySetChain(PolicySetChainUpdateVO updateVO);

    /**
     * 提交
     *
     * @param id id
     */
    void submit(Long id);

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
}
