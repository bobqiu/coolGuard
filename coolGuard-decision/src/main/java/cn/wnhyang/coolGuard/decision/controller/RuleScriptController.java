package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.RuleScriptConvert;
import cn.wnhyang.coolGuard.decision.service.RuleScriptService;
import cn.wnhyang.coolGuard.decision.vo.RuleScriptVO;
import cn.wnhyang.coolGuard.decision.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.RuleScriptUpdateVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;

/**
 * 规则脚本
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@RestController
@RequestMapping("/ruleScript")
@RequiredArgsConstructor
public class RuleScriptController {

    private final RuleScriptService ruleScriptService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:ruleScript:create")
    @OperateLog(module = "后台-规则脚本", name = "创建规则脚本", type = OperateType.CREATE)
    public CommonResult<Long> createRuleScript(@RequestBody @Valid RuleScriptCreateVO createVO) {
        return success(ruleScriptService.createRuleScript(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:ruleScript:update")
    @OperateLog(module = "后台-规则脚本", name = "更新规则脚本", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateRuleScript(@RequestBody @Valid RuleScriptUpdateVO updateVO) {
        ruleScriptService.updateRuleScript(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:ruleScript:delete")
    @OperateLog(module = "后台-规则脚本", name = "删除规则脚本", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteRuleScript(@RequestParam("id") Long id) {
        ruleScriptService.deleteRuleScript(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<RuleScriptVO> getRuleScript(@RequestParam("id") Long id) {
        return success(RuleScriptConvert.INSTANCE.convert(ruleScriptService.getRuleScript(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<RuleScriptVO>> pageRuleScript(@Valid RuleScriptPageVO pageVO) {
        return success(RuleScriptConvert.INSTANCE.convert(ruleScriptService.pageRuleScript(pageVO)));
    }
}
