package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.service.PolicyService;
import cn.wnhyang.coolguard.decision.vo.PolicyVO;
import cn.wnhyang.coolguard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolguard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.PolicyCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicyPageVO;
import cn.wnhyang.coolguard.decision.vo.update.PolicyUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 策略
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:policy:create")
    @OperateLog(module = "后台-策略", name = "创建策略", type = OperateType.CREATE)
    public CommonResult<Long> createPolicy(@RequestBody @Valid PolicyCreateVO createVO) {
        return success(policyService.createPolicy(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:policy:update")
    @OperateLog(module = "后台-策略", name = "更新策略", type = OperateType.UPDATE)
    public CommonResult<Boolean> updatePolicy(@RequestBody @Valid PolicyUpdateVO updateVO) {
        policyService.updatePolicy(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:policy:delete")
    @OperateLog(module = "后台-策略", name = "删除策略", type = OperateType.DELETE)
    public CommonResult<Boolean> deletePolicy(@RequestParam("id") Long id) {
        policyService.deletePolicy(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    @SaCheckLogin
    public CommonResult<PolicyVO> getPolicy(@RequestParam("id") Long id) {
        return success(policyService.getPolicy(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    @SaCheckLogin
    public CommonResult<PageResult<PolicyVO>> pagePolicy(@Valid PolicyPageVO pageVO) {
        return success(policyService.pagePolicy(pageVO));
    }

    /**
     * 提交
     *
     * @param submitVO submitVO
     * @return true/false
     */
    @PostMapping("/submit")
    @SaCheckPermission("decision:policy:submit")
    @OperateLog(module = "后台-策略", name = "提交策略", type = OperateType.CREATE)
    public CommonResult<VersionSubmitResultVO> submit(@RequestBody @Valid VersionSubmitVO submitVO) {
        return success(policyService.submit(submitVO));
    }

    /**
     * 批量提交
     *
     * @param submitVOList 提交VO列表
     * @return true/false
     */
    @PostMapping("/batchSubmit")
    @SaCheckPermission("decision:policy:submit")
    @SaCheckLogin
    public CommonResult<List<VersionSubmitResultVO>> batchSubmit(@RequestBody @Valid BatchVersionSubmit submitVOList) {
        return success(policyService.batchSubmit(submitVOList));
    }

    /**
     * 根据策略集编码查询列表
     *
     * @param setCode 策略集编码
     * @return list
     */
    @GetMapping("/list")
    @SaCheckLogin
    public CommonResult<List<PolicyVO>> listByPolicySetCode(@RequestParam("setCode") String setCode) {
        return success(policyService.listByPolicySetCode(setCode));
    }

    /**
     * 获取策略lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policyService.getLabelValueList());
    }
}
