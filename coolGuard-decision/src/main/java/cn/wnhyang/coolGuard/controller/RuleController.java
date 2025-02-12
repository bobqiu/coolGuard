package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleService;
import cn.wnhyang.coolGuard.vo.RuleVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import cn.wnhyang.coolGuard.vo.page.RulePageVO;
import cn.wnhyang.coolGuard.vo.update.RuleUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

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
    public CommonResult<List<RuleVO>> listByPolicyCode(@RequestParam("policyCode") String policyCode) {
        return success(ruleService.listByPolicyCode(policyCode));
    }

    /**
     * 获取规则lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(ruleService.getLabelValueList());
    }
}
