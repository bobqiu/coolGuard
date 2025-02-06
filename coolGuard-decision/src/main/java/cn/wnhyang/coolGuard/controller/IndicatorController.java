package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import cn.wnhyang.coolGuard.enums.WinSize;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorService;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import cn.wnhyang.coolGuard.vo.VersionSubmitResultVO;
import cn.wnhyang.coolGuard.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorByPolicySetPageVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorPageVO;
import cn.wnhyang.coolGuard.vo.update.IndicatorUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 提交指标
     *
     * @param submitVO 提交VO
     * @return true/false
     */
    @PostMapping("/submit")
    public CommonResult<VersionSubmitResultVO> submit(@RequestBody @Valid VersionSubmitVO submitVO) {
        return success(indicatorService.submit(submitVO));
    }

    /**
     * 批量提交指标
     *
     * @param submitVOList 提交VO列表
     * @return true/false
     */
    @PostMapping("/batchSubmit")
    public CommonResult<List<VersionSubmitResultVO>> batchSubmit(@RequestBody @Valid BatchVersionSubmit submitVOList) {
        return success(indicatorService.batchSubmit(submitVOList));
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<IndicatorVO> getIndicator(@RequestParam("id") Long id) {
        return success(indicatorService.getIndicator(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<IndicatorVO>> pageIndicator(@Valid IndicatorPageVO pageVO) {
        return success(indicatorService.pageIndicator(pageVO));
    }

    /**
     * 分页查询根据策略集id
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/policySet/page")
    public CommonResult<PageResult<IndicatorVO>> pageIndicatorByPolicySet(@Valid IndicatorByPolicySetPageVO pageVO) {
        return success(IndicatorConvert.INSTANCE.convert(indicatorService.pageIndicatorByPolicySet(pageVO)));
    }

    /**
     * 查询全部指标
     *
     * @return 全部指标
     */
    @GetMapping("/list")
    public CommonResult<List<IndicatorVO>> listIndicator() {
        return success(indicatorService.listIndicator());
    }

    /**
     * 获取指标类型
     *
     * @return 指标类型
     */
    @GetMapping("/indicatorType")
    public CommonResult<List<LabelValue>> getIndicatorType() {
        return success(IndicatorType.getLvList());
    }

    /**
     * 获取时间单位
     *
     * @return 时间单位
     */
    @GetMapping("/winSize")
    public CommonResult<List<LabelValue>> getWinSize() {
        return success(WinSize.getLvList());
    }
}
