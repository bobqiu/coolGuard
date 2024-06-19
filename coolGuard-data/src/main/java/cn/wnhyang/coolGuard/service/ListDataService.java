package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.ListData;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.ListDataCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListDataPageVO;
import cn.wnhyang.coolGuard.vo.update.ListDataUpdateVO;

import java.util.List;

/**
 * 名单数据表 服务类
 *
 * @author wnhyang
 * @since 2024/05/28
 */
public interface ListDataService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(ListDataCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(ListDataUpdateVO updateVO);

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
    ListData get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<ListData> page(ListDataPageVO pageVO);

    /**
     * 获取名单数据
     *
     * @param setId 名单集id
     * @return 名单数据
     */
    List<String> getListData(Long setId);

    /**
     * 判断名单数据是否存在
     *
     * @param setId 名单集id
     * @param value 名单数据
     * @return true存在/false不存在
     */
    boolean hasListData(Long setId, String value);

}
