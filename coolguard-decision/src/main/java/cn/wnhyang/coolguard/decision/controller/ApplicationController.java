package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.convert.ApplicationConvert;
import cn.wnhyang.coolguard.decision.service.ApplicationService;
import cn.wnhyang.coolguard.decision.vo.ApplicationVO;
import cn.wnhyang.coolguard.decision.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.ApplicationPageVO;
import cn.wnhyang.coolguard.decision.vo.update.ApplicationUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 应用
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:application:create")
    @OperateLog(module = "后台-应用", name = "创建应用", type = OperateType.CREATE)
    public CommonResult<Long> createApplication(@RequestBody @Valid ApplicationCreateVO createVO) {
        return success(applicationService.createApplication(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:application:update")
    @OperateLog(module = "后台-应用", name = "更新应用", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateApplication(@RequestBody @Valid ApplicationUpdateVO updateVO) {
        applicationService.updateApplication(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:application:delete")
    @OperateLog(module = "后台-应用", name = "删除应用", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteApplication(@RequestParam("id") Long id) {
        applicationService.deleteApplication(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    @SaCheckLogin
    public CommonResult<ApplicationVO> getApplication(@RequestParam("id") Long id) {
        return success(ApplicationConvert.INSTANCE.convert(applicationService.getApplication(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    @SaCheckLogin
    public CommonResult<PageResult<ApplicationVO>> pageApplication(@Valid ApplicationPageVO pageVO) {
        return success(ApplicationConvert.INSTANCE.convert(applicationService.pageApplication(pageVO)));
    }

    /**
     * 获取应用lv列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(applicationService.getLabelValueList());
    }
}
