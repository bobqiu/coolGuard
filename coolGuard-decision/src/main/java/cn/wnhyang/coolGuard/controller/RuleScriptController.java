package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.RuleScriptConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleScriptService;
import cn.wnhyang.coolGuard.vo.RuleScriptVO;
import cn.wnhyang.coolGuard.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolGuard.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolGuard.vo.update.RuleScriptUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 规则脚本表
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
    @GetMapping("/{id}")
    public CommonResult<RuleScriptVO> getRuleScript(@PathVariable("id") Long id) {
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
