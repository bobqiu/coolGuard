package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.service.RuleService;
import cn.wnhyang.coolguard.decision.vo.RuleVO;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.RuleCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.RulePageVO;
import cn.wnhyang.coolguard.decision.vo.update.RuleUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 规则
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:rule:create")
    @OperateLog(module = "后台-规则", name = "创建规则", type = OperateType.CREATE)
    public CommonResult<Long> createRule(@RequestBody @Valid RuleCreateVO createVO) {
        return success(ruleService.createRule(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:rule:update")
    @OperateLog(module = "后台-规则", name = "更新规则", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateRule(@RequestBody @Valid RuleUpdateVO updateVO) {
        ruleService.updateRule(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:rule:delete")
    @OperateLog(module = "后台-规则", name = "删除规则", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteRule(@RequestParam("id") Long id) {
        ruleService.deleteRule(id);
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
    public CommonResult<RuleVO> getRule(@RequestParam("id") Long id) {
        return success(ruleService.getRule(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    @SaCheckLogin
    public CommonResult<PageResult<RuleVO>> pageRule(@Valid RulePageVO pageVO) {
        return success(ruleService.pageRule(pageVO));
    }

    /**
     * 提交
     *
     * @param submitVO submitVO
     * @return true/false
     */
    @PostMapping("/submit")
    @SaCheckPermission("decision:rule:submit")
    @OperateLog(module = "后台-规则", name = "提交规则", type = OperateType.CREATE)
    public CommonResult<Boolean> submit(@RequestBody @Valid VersionSubmitVO submitVO) {
        ruleService.submit(submitVO);
        return success(true);
    }

    /**
     * 根据策略编码查询列表
     *
     * @param policyCode 策略编码
     * @return list
     */
    @GetMapping("/list")
    @SaCheckLogin
    public CommonResult<List<RuleVO>> listByPolicyCode(@RequestParam("policyCode") String policyCode) {
        return success(ruleService.listByPolicyCode(policyCode));
    }

    /**
     * 获取规则lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(ruleService.getLabelValueList());
    }
}
