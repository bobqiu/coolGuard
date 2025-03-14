package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Action;
import cn.wnhyang.coolguard.decision.entity.ListData;
import cn.wnhyang.coolguard.decision.vo.create.ListDataCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.ListDataPageVO;
import cn.wnhyang.coolguard.decision.vo.update.ListDataUpdateVO;

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
     * @param setCode 名单集code
     * @return 名单数据
     */
    List<String> getListData(String setCode);

    /**
     * 判断名单数据是否存在
     *
     * @param setCode 名单集code
     * @param value   名单数据
     * @return true存在/false不存在
     */
    boolean hasListData(String setCode, String value);

    /**
     * 添加名单数据
     *
     * @param addLists 添加名单数据
     */
    void addListData(List<Action.AddList> addLists);

}
