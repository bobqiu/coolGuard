package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.RuleConditionConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleConditionService;
import cn.wnhyang.coolGuard.vo.RuleConditionVO;
import cn.wnhyang.coolGuard.vo.create.RuleConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.RuleConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.RuleConditionUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 规则条件
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/ruleCondition")
@RequiredArgsConstructor
public class RuleConditionController {

    private final RuleConditionService ruleConditionService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createRuleCondition(@RequestBody @Valid RuleConditionCreateVO createVO) {
        return success(ruleConditionService.createRuleCondition(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateRuleCondition(@RequestBody @Valid RuleConditionUpdateVO updateVO) {
        ruleConditionService.updateRuleCondition(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteRuleCondition(@RequestParam("id") Long id) {
        ruleConditionService.deleteRuleCondition(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<RuleConditionVO> getRuleCondition(@PathVariable("id") Long id) {
        return success(RuleConditionConvert.INSTANCE.convert(ruleConditionService.getRuleCondition(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<RuleConditionVO>> pageRuleCondition(@Valid RuleConditionPageVO pageVO) {
        return success(RuleConditionConvert.INSTANCE.convert(ruleConditionService.pageRuleCondition(pageVO)));
    }
}
