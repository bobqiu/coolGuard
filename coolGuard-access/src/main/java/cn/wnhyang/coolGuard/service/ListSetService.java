package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.ListSet;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.ListSetCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListSetPageVO;
import cn.wnhyang.coolGuard.vo.update.ListSetUpdateVO;

/**
 * 名单集表 服务类
 *
 * @author wnhyang
 * @since 2024/05/28
 */
public interface ListSetService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(ListSetCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(ListSetUpdateVO updateVO);

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
    ListSet get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<ListSet> page(ListSetPageVO pageVO);

}
