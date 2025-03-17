package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.PolicyVersion;
import cn.wnhyang.coolguard.decision.vo.PolicyVersionVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicyVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;

import java.util.List;

/**
 * 策略版本表 服务类
 *
 * @author wnhyang
 * @since 2025/02/11
 */
public interface PolicyVersionService {

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    PolicyVersion get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicyVersion> page(PolicyVersionPageVO pageVO);

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicyVersionVO> pageByCode(PolicyVersionPageVO pageVO);

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
     * 根据code查询
     *
     * @param code code
     * @return pageResult
     */
    List<PolicyVersion> getByCode(String code);

    /**
     * 获取lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 根据code和version查询
     *
     * @param queryVO queryVO
     * @return vo
     */
    PolicyVersionVO getByCv(CvQueryVO queryVO);
}
