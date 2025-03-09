package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.service.PolicySetService;
import cn.wnhyang.coolGuard.decision.vo.PolicySetVO;
import cn.wnhyang.coolGuard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.decision.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.PolicySetUpdateVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;

/**
 * 策略集
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/policySet")
@RequiredArgsConstructor
public class PolicySetController {

    private final PolicySetService policySetService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:policySet:create")
    @OperateLog(module = "后台-策略集", name = "创建策略集", type = OperateType.CREATE)
    public CommonResult<Long> createPolicySet(@RequestBody @Valid PolicySetCreateVO createVO) {
        return success(policySetService.createPolicySet(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:policySet:update")
    @OperateLog(module = "后台-策略集", name = "更新策略集", type = OperateType.UPDATE)
    public CommonResult<Boolean> updatePolicySet(@RequestBody @Valid PolicySetUpdateVO updateVO) {
        policySetService.updatePolicySet(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:policySet:delete")
    @OperateLog(module = "后台-策略集", name = "删除策略集", type = OperateType.DELETE)
    public CommonResult<Boolean> deletePolicySet(@RequestParam("id") Long id) {
        policySetService.deletePolicySet(id);
        return success(true);
    }

    /**
     * 提交
     *
     * @param submitVO submitVO
     * @return true/false
     */
    @PostMapping("/submit")
    @SaCheckPermission("decision:policySet:submit")
    @OperateLog(module = "后台-策略集", name = "提交策略集", type = OperateType.CREATE)
    public CommonResult<Boolean> submit(@RequestBody @Valid VersionSubmitVO submitVO) {
        policySetService.submit(submitVO);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<PolicySetVO> getPolicySet(@RequestParam("id") Long id) {
        return success(policySetService.getPolicySet(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<PolicySetVO>> pagePolicySet(@Valid PolicySetPageVO pageVO) {
        return success(policySetService.pagePolicySet0(pageVO));
    }

    /**
     * 列表查询
     *
     * @return list
     */
    @GetMapping("/list")
    public CommonResult<List<PolicySetVO>> listPolicySet() {
        return success(policySetService.listPolicySet());
    }

    /**
     * 获取策略集lv列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policySetService.getLabelValueList());
    }

}
