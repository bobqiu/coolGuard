package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetUpdateVO;

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

}
