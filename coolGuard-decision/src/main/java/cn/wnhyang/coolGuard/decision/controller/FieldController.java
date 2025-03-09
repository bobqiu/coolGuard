package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.FieldConvert;
import cn.wnhyang.coolGuard.decision.service.FieldService;
import cn.wnhyang.coolGuard.decision.vo.FieldVO;
import cn.wnhyang.coolGuard.decision.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.decision.vo.create.TestDynamicFieldScript;
import cn.wnhyang.coolGuard.decision.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.FieldUpdateVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;

/**
 * 字段
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@RestController
@RequestMapping("/field")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:field:create")
    @OperateLog(module = "后台-字段", name = "创建字段", type = OperateType.CREATE)
    public CommonResult<Long> createField(@RequestBody @Valid FieldCreateVO createVO) {
        return success(fieldService.createField(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:field:update")
    @OperateLog(module = "后台-字段", name = "更新字段", type = OperateType.UPDATE)
    public CommonResult<Boolean> updateField(@RequestBody @Valid FieldUpdateVO updateVO) {
        fieldService.updateField(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:field:delete")
    @OperateLog(module = "后台-字段", name = "删除字段", type = OperateType.DELETE)
    public CommonResult<Boolean> deleteField(@RequestParam("id") Long id) {
        fieldService.deleteField(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<FieldVO> getField(@RequestParam("id") Long id) {
        return success(FieldConvert.INSTANCE.convert(fieldService.getField(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<FieldVO>> pageField(@Valid FieldPageVO pageVO) {
        return success(FieldConvert.INSTANCE.convert(fieldService.pageField(pageVO)));
    }

    /**
     * 测试动态脚本
     *
     * @param testDynamicFieldScript 测试动态脚本
     * @return 测试结果
     */
    @PostMapping("/testDynamicFieldScript")
    public CommonResult<String> testDynamicFieldScript(@RequestBody @Valid TestDynamicFieldScript testDynamicFieldScript) {
        return success(fieldService.testDynamicFieldScript(testDynamicFieldScript));
    }

    /**
     * 查询列表
     *
     * @return list
     */
    @GetMapping("/list")
    public CommonResult<List<FieldVO>> listField() {
        return success(FieldConvert.INSTANCE.convert(fieldService.listField()));
    }

}
