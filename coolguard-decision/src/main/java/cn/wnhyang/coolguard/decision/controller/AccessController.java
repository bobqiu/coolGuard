package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.service.AccessService;
import cn.wnhyang.coolguard.decision.vo.AccessVO;
import cn.wnhyang.coolguard.decision.vo.create.AccessCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.AccessPageVO;
import cn.wnhyang.coolguard.decision.vo.update.AccessUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 接入
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:access:create")
    @OperateLog(module = "后台-接入", name = "创建接入", type = OperateType.CREATE)
    public CommonResult<Long> createAccess(@RequestBody @Valid AccessCreateVO createVO) {
        return success(accessService.createAccess(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:access:update")
    @OperateLog(module = "后台-接入", name = "更新接入", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateAccess(@RequestBody @Valid AccessUpdateVO updateVO) {
        accessService.updateAccess(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:access:delete")
    @OperateLog(module = "后台-接入", name = "删除接入", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteAccess(@RequestParam("id") Long id) {
        accessService.deleteAccess(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<AccessVO> getAccess(@RequestParam("id") Long id) {
        return success(accessService.getAccess(id));
    }

    /**
     * 查询单个
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<AccessVO> getAccessByCode(@RequestParam("code") String code) {
        return success(accessService.getAccessByCode(code));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<AccessVO>> pageAccess(@Valid AccessPageVO pageVO) {
        return success(accessService.pageAccess(pageVO));
    }

}
