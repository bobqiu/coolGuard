package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicySetVersionVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 策略集表版本 服务类
 *
 * @author wnhyang
 * @since 2024/11/30
 */
public interface PolicySetVersionService {

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
    PolicySetVersion get(Long id);

    /**
     * 根据code查询
     *
     * @param code code
     * @return pageResult
     */
    PolicySetVersion getByCode(String code);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVersion> page(PolicySetVersionPageVO pageVO);

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicySetVersionVO> pageByCode(@Valid PolicySetVersionPageVO pageVO);

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
