package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ApplicationConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ApplicationService;
import cn.wnhyang.coolGuard.vo.ApplicationVO;
import cn.wnhyang.coolGuard.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolGuard.vo.page.ApplicationPageVO;
import cn.wnhyang.coolGuard.vo.update.ApplicationUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

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
    @GetMapping("/{id}")
    public CommonResult<ApplicationVO> getApplication(@PathVariable("id") Long id) {
        return success(ApplicationConvert.INSTANCE.convert(applicationService.getApplication(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ApplicationVO>> pageApplication(@Valid ApplicationPageVO pageVO) {
        return success(ApplicationConvert.INSTANCE.convert(applicationService.pageApplication(pageVO)));
    }
}
