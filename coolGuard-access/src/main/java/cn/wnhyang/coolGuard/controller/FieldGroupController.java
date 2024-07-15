package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldGroupService;
import cn.wnhyang.coolGuard.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

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
    @GetMapping("/{id}")
    public CommonResult<FieldGroupVO> getFieldGroup(@PathVariable("id") Long id) {
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
}
