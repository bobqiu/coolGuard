package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.StrategySetConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.StrategySetService;
import cn.wnhyang.coolGuard.vo.StrategySetVO;
import cn.wnhyang.coolGuard.vo.create.StrategySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategySetPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategySetUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/strategySet")
@RequiredArgsConstructor
public class StrategySetController {

    private final StrategySetService strategySetService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createStrategySet(@RequestBody @Valid StrategySetCreateVO createVO) {
        return success(strategySetService.createStrategySet(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateStrategySet(@RequestBody @Valid StrategySetUpdateVO updateVO) {
        strategySetService.updateStrategySet(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteStrategySet(@RequestParam("id") Long id) {
        strategySetService.deleteStrategySet(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<StrategySetVO> getStrategySet(@PathVariable("id") Long id) {
        return success(StrategySetConvert.INSTANCE.convert(strategySetService.getStrategySet(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<StrategySetVO>> pageStrategySet(@Valid StrategySetPageVO pageVO) {
        return success(StrategySetConvert.INSTANCE.convert(strategySetService.pageStrategySet(pageVO)));
    }
}
