package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.DisposalConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.DisposalService;
import cn.wnhyang.coolGuard.vo.DisposalVO;
import cn.wnhyang.coolGuard.vo.create.DisposalCreateVO;
import cn.wnhyang.coolGuard.vo.page.DisposalPageVO;
import cn.wnhyang.coolGuard.vo.update.DisposalUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

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

    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(disposalService.getLabelValueList());
    }
}
