package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.vo.page.DisposalPageVO;
import cn.wnhyang.coolGuard.vo.update.DisposalUpdateVO;

import java.util.List;

/**
 * 处置表 服务类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
public interface DisposalService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createDisposal(DisposalCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateDisposal(DisposalUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteDisposal(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    Disposal getDisposal(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<Disposal> pageDisposal(DisposalPageVO pageVO);

    /**
     * 查询列表
     *
     * @return list
     */
    List<Disposal> listDisposal();

    /**
     * 获取标签值列表
     *
     * @return list
     */
    List<LabelValue> getLabelValueList();
}
