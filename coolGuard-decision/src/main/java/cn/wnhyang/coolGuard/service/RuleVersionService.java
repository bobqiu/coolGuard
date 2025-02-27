package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.RuleVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.RuleVersionVO;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
import jakarta.validation.Valid;

import java.util.List;

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

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<RuleVersionVO> pageByCode(@Valid RuleVersionPageVO pageVO);

    /**
     * 下线
     *
     * @param id id
     */
    void offline(Long id);

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
}
