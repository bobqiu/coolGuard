package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Tag;
import cn.wnhyang.coolguard.decision.vo.create.TagCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.TagPageVO;
import cn.wnhyang.coolguard.decision.vo.update.TagUpdateVO;

import java.util.List;

/**
 * 标签表 服务类
 *
 * @author wnhyang
 * @since 2024/12/08
 */
public interface TagService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(TagCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(TagUpdateVO updateVO);

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
    Tag get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Tag> page(TagPageVO pageVO);

    /**
     * 获取 lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 添加标签
     *
     * @param tagCodes tagCodes
     */
    void addTag(List<String> tagCodes);
}
