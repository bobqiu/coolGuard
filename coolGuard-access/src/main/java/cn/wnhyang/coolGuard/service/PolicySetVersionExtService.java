package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.PolicySetVersionExt;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionExtCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionExtPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionExtUpdateVO;

/**
 * 策略集版本扩展表 服务类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
public interface PolicySetVersionExtService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(PolicySetVersionExtCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(PolicySetVersionExtUpdateVO updateVO);

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
    PolicySetVersionExt get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVersionExt> page(PolicySetVersionExtPageVO pageVO);

}
