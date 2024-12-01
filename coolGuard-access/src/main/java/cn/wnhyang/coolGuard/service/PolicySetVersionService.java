package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;

/**
 * 策略集表版本 服务类
 *
 * @author wnhyang
 * @since 2024/11/30
 */
public interface PolicySetVersionService {

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
    PolicySetVersion get(Long id);

    /**
     * 根据code查询
     *
     * @param code code
     * @return pageResult
     */
    PolicySetVersion getByCode(String code);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVersion> page(PolicySetVersionPageVO pageVO);

}
