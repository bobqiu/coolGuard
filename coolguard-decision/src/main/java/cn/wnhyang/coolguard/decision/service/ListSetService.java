package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.ListSet;
import cn.wnhyang.coolguard.decision.vo.create.ListSetCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.ListSetPageVO;
import cn.wnhyang.coolguard.decision.vo.update.ListSetUpdateVO;

import java.util.List;

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

    /**
     * 获取lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();
}
