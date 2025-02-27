package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.IndicatorSimpleVO;
import cn.wnhyang.coolGuard.vo.IndicatorVersionVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorVersionPageVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 指标表版本表 服务类
 *
 * @author wnhyang
 * @since 2024/11/21
 */
public interface IndicatorVersionService {

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
    IndicatorVersionVO get(Long id);

    /**
     * 根据code查询
     *
     * @param code code
     * @return po
     */
    IndicatorVersionVO getByCode(String code);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<IndicatorVersionVO> page(IndicatorVersionPageVO pageVO);

    /**
     * 下线
     *
     * @param id id
     */
    void offline(Long id);

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<IndicatorVersionVO> pageByCode(@Valid IndicatorVersionPageVO pageVO);

    /**
     * 选中
     *
     * @param id id
     */
    void chose(Long id);

    /**
     * 获取lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 获取simpleList
     *
     * @return simpleList
     */
    List<IndicatorSimpleVO> getSimpleList();
}
