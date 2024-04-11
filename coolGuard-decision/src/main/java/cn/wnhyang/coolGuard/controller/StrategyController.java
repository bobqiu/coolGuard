package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.StrategyConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.StrategyService;
import cn.wnhyang.coolGuard.vo.StrategyVO;
import cn.wnhyang.coolGuard.vo.create.StrategyCreateVO;
import cn.wnhyang.coolGuard.vo.page.StrategyPageVO;
import cn.wnhyang.coolGuard.vo.update.StrategyUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/strategy")
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService strategyService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createStrategy(@RequestBody @Valid StrategyCreateVO createVO) {
        return success(strategyService.createStrategy(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateStrategy(@RequestBody @Valid StrategyUpdateVO updateVO) {
        strategyService.updateStrategy(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteStrategy(@RequestParam("id") Long id) {
        strategyService.deleteStrategy(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<StrategyVO> getStrategy(@PathVariable("id") Long id) {
        return success(StrategyConvert.INSTANCE.convert(strategyService.getStrategy(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<StrategyVO>> pageStrategy(@Valid StrategyPageVO pageVO) {
        return success(StrategyConvert.INSTANCE.convert(strategyService.pageStrategy(pageVO)));
    }
}
