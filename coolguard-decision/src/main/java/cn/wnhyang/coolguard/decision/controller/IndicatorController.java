package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.service.IndicatorService;
import cn.wnhyang.coolguard.decision.vo.IndicatorVO;
import cn.wnhyang.coolguard.decision.vo.VersionSubmitResultVO;
import cn.wnhyang.coolguard.decision.vo.base.BatchVersionSubmit;
import cn.wnhyang.coolguard.decision.vo.base.VersionSubmitVO;
import cn.wnhyang.coolguard.decision.vo.create.IndicatorCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.IndicatorPageVO;
import cn.wnhyang.coolguard.decision.vo.update.IndicatorUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

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
    @SaCheckPermission("decision:indicator:create")
    @OperateLog(module = "后台-指标", name = "创建指标", type = OperateType.CREATE)
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
    @SaCheckPermission("decision:indicator:update")
    @OperateLog(module = "后台-指标", name = "更新指标", type = OperateType.UPDATE)
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
    @SaCheckPermission("decision:indicator:delete")
    @OperateLog(module = "后台-指标", name = "删除指标", type = OperateType.DELETE)
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
    @SaCheckPermission("decision:indicator:submit")
    @OperateLog(module = "后台-指标", name = "提交指标", type = OperateType.CREATE)
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
    @SaCheckPermission("decision:indicator:submit")
    @SaCheckLogin
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
    @SaCheckLogin
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
    @SaCheckLogin
    public CommonResult<PageResult<IndicatorVO>> pageIndicator(@Valid IndicatorPageVO pageVO) {
        return success(indicatorService.pageIndicator(pageVO));
    }

    /**
     * 查询全部指标
     *
     * @return 全部指标
     */
    @GetMapping("/list")
    @SaCheckLogin
    public CommonResult<List<IndicatorVO>> listIndicator() {
        return success(indicatorService.listIndicator());
    }

    /**
     * 获取lvList
     *
     * @return lvList
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(indicatorService.getLabelValueList());
    }
}
