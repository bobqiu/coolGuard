package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorByStrategySetPageVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 指标
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@RestController
@RequestMapping("/indicator")
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorService indicatorService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createIndicator(@RequestBody @Valid IndicatorCreateVO createVO) {
        return success(indicatorService.createIndicator(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateIndicator(@RequestBody @Valid IndicatorUpdateVO updateVO) {
        indicatorService.updateIndicator(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteIndicator(@RequestParam("id") Long id) {
        indicatorService.deleteIndicator(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<IndicatorVO> getIndicator(@PathVariable("id") Long id) {
        return success(IndicatorConvert.INSTANCE.convert(indicatorService.getIndicator(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<IndicatorVO>> pageIndicator(@Valid IndicatorPageVO pageVO) {
        return success(IndicatorConvert.INSTANCE.convert(indicatorService.pageIndicator(pageVO)));
    }

    /**
     * 分页查询根据策略集id
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/strategySet/page")
    public CommonResult<PageResult<IndicatorVO>> pageIndicatorByStrategySet(@Valid IndicatorByStrategySetPageVO pageVO) {
        return success(IndicatorConvert.INSTANCE.convert(indicatorService.pageIndicatorByStrategySet(pageVO)));
    }
}
