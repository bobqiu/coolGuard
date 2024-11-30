package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.PolicyVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionPageVO;

/**
 * 策略版本表 服务类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
public interface PolicyVersionService {

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    PolicyVersion get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicyVersion> page(PolicyVersionPageVO pageVO);

}
