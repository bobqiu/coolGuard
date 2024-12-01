package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;

/**
 * 规则版本表 服务类
 *
 * @author wnhyang
 * @since 2024/08/29
 */
public interface RuleVersionService {

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
    RuleVersion get(Long id);

    /**
     * 根据code查询
     *
     * @param code code
     * @return po
     */
    RuleVersion getByCode(String code);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<RuleVersion> page(RuleVersionPageVO pageVO);

}
