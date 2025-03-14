package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.vo.AccessVO;
import cn.wnhyang.coolguard.decision.vo.create.AccessCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.AccessPageVO;
import cn.wnhyang.coolguard.decision.vo.update.AccessUpdateVO;

/**
 * 接入表 服务类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
public interface AccessService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createAccess(AccessCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateAccess(AccessUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteAccess(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    AccessVO getAccess(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<AccessVO> pageAccess(AccessPageVO pageVO);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return po
     */
    AccessVO getAccessByCode(String code);

}
