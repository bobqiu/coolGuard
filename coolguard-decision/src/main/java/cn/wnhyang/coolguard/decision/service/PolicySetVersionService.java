package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.PolicySetVersion;
import cn.wnhyang.coolguard.decision.vo.PolicySetVersionVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;

import java.util.List;

/**
 * 策略集表版本 服务类
 *
 * @author wnhyang
 * @since 2024/11/30
 */
public interface PolicySetVersionService {

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
    List<PolicySetVersion> getByCode(String code);

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
    PageResult<PolicySetVersionVO> pageByCode(PolicySetVersionPageVO pageVO);

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

    /**
     * 根据code和version查询
     *
     * @param queryVO queryVO
     * @return vo
     */
    PolicySetVersionVO getByCv(CvQueryVO queryVO);
}
