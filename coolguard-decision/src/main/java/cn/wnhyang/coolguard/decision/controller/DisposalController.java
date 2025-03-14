package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.convert.DisposalConvert;
import cn.wnhyang.coolguard.decision.service.DisposalService;
import cn.wnhyang.coolguard.decision.vo.DisposalVO;
import cn.wnhyang.coolguard.decision.vo.create.DisposalCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.DisposalPageVO;
import cn.wnhyang.coolguard.decision.vo.update.DisposalUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 处置
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@RestController
@RequestMapping("/disposal")
@RequiredArgsConstructor
public class DisposalController {

    private final DisposalService disposalService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:disposal:create")
    @OperateLog(module = "后台-处置", name = "创建处置", type = OperateType.CREATE)
    public CommonResult<Long> createDisposal(@RequestBody @Valid DisposalCreateVO createVO) {
        return success(disposalService.createDisposal(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:disposal:update")
    @OperateLog(module = "后台-处置", name = "更新处置", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateDisposal(@RequestBody @Valid DisposalUpdateVO updateVO) {
        disposalService.updateDisposal(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:disposal:delete")
    @OperateLog(module = "后台-处置", name = "删除处置", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteDisposal(@RequestParam("id") Long id) {
        disposalService.deleteDisposal(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<DisposalVO> getDisposal(@RequestParam("id") Long id) {
        return success(DisposalConvert.INSTANCE.convert(disposalService.getDisposal(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<DisposalVO>> pageDisposal(@Valid DisposalPageVO pageVO) {
        return success(DisposalConvert.INSTANCE.convert(disposalService.pageDisposal(pageVO)));
    }

    /**
     * 获取列表
     *
     * @return list
     */
    @GetMapping("/list")
    public CommonResult<List<DisposalVO>> listDisposal() {
        return success(DisposalConvert.INSTANCE.convert(disposalService.listDisposal()));
    }

    /**
     * 获取应用处置列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(disposalService.getLabelValueList());
    }
}
