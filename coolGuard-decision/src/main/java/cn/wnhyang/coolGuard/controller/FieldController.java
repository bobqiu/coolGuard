package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.FieldConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldService;
import cn.wnhyang.coolGuard.vo.FieldVO;
import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 字段表
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
    @GetMapping("/{id}")
    public CommonResult<FieldVO> getField(@PathVariable("id") Long id) {
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

}
