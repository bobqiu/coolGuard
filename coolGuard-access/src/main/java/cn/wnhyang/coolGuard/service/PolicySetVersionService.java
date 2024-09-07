package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionUpdateVO;

/**
 * 策略集版本表 服务类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
public interface PolicySetVersionService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(PolicySetVersionCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(PolicySetVersionUpdateVO updateVO);

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
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVersion> page(PolicySetVersionPageVO pageVO);

}
