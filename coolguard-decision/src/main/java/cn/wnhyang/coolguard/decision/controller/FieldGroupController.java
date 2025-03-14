package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.service.FieldGroupService;
import cn.wnhyang.coolguard.decision.vo.FieldGroupVO;
import cn.wnhyang.coolguard.decision.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolguard.decision.vo.update.FieldGroupUpdateVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 字段分组
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@RestController
@RequestMapping("/fieldGroup")
@RequiredArgsConstructor
public class FieldGroupController {

    private final FieldGroupService fieldGroupService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:fieldGroup:create")
    @OperateLog(module = "后台-字段分组", name = "创建字段分组", type = OperateType.CREATE)
    public CommonResult<Long> createFieldGroup(@RequestBody @Valid FieldGroupCreateVO createVO) {
        return success(fieldGroupService.createFieldGroup(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:fieldGroup:update")
    @OperateLog(module = "后台-字段分组", name = "更新字段分组", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateFieldGroup(@RequestBody @Valid FieldGroupUpdateVO updateVO) {
        fieldGroupService.updateFieldGroup(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:fieldGroup:delete")
    @OperateLog(module = "后台-字段分组", name = "删除字段分组", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteFieldGroup(@RequestParam("id") Long id) {
        fieldGroupService.deleteFieldGroup(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<FieldGroupVO> getFieldGroup(@RequestParam("id") Long id) {
        return success(fieldGroupService.getFieldGroup(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<FieldGroupVO>> pageFieldGroup(@Valid FieldGroupPageVO pageVO) {
        return success(fieldGroupService.pageFieldGroup(pageVO));
    }

    /**
     * 查询列表
     *
     * @return list
     */
    @GetMapping("/list")
    public CommonResult<List<FieldGroupVO>> listFieldGroup() {
        return success(fieldGroupService.listFieldGroup());
    }

    /**
     * 获取字段分组lv列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(fieldGroupService.getLabelValueList());
    }
}
