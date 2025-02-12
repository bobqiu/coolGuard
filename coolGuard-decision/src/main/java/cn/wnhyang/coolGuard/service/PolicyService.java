package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicyUpdateVO;

import java.util.Collection;
import java.util.List;

/**
 * 策略表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface PolicyService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createPolicy(PolicyCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updatePolicy(PolicyUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deletePolicy(Long id);

    /**
     * 批量删除，与单删除区别是，已知id存在
     * 另外主要用于策略集删除时关联删除
     *
     * @param ids id集合
     */
    void deletePolicy(Collection<Long> ids);

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    PolicyVO getPolicy(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<PolicyVO> pagePolicy(PolicyPageVO pageVO);

    /**
     * 提交
     *
     * @param submitVO submitVO
     */
    void submit(VersionSubmitVO submitVO);

    /**
     * 根据策略集code查询列表
     *
     * @param setCode 策略集code
     * @return list
     */
    List<PolicyVO> listByPolicySetCode(String setCode);

    /**
     * 获取标签值列表
     *
     * @return list
     */
    List<LabelValue> getLabelValueList();
}
