package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ConditionConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ConditionService;
import cn.wnhyang.coolGuard.vo.ConditionVO;
import cn.wnhyang.coolGuard.vo.create.ConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.ConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.ConditionUpdateVO;
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
@RequestMapping("/condition")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createCondition(@RequestBody @Valid ConditionCreateVO createVO) {
        return success(conditionService.createCondition(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateCondition(@RequestBody @Valid ConditionUpdateVO updateVO) {
        conditionService.updateCondition(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteCondition(@RequestParam("id") Long id) {
        conditionService.deleteCondition(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<ConditionVO> getCondition(@PathVariable("id") Long id) {
        return success(ConditionConvert.INSTANCE.convert(conditionService.getCondition(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ConditionVO>> pageCondition(@Valid ConditionPageVO pageVO) {
        return success(ConditionConvert.INSTANCE.convert(conditionService.pageCondition(pageVO)));
    }
}
