package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicyUpdateVO;

/**
 * 策略表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface PolicyService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createPolicy(PolicyCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updatePolicy(PolicyUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deletePolicy(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Policy getPolicy(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Policy> pagePolicy(PolicyPageVO pageVO);

}
