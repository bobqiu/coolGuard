package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.FieldRefConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.FieldRefService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.FieldRefVO;
import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldRefPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldRefUpdateVO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Slf4j
@RestController
@RequestMapping("/fieldRef")
@RequiredArgsConstructor
public class FieldRefController {

    private final FieldRefService fieldRefService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid FieldRefCreateVO createVO) {
        return success(fieldRefService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid FieldRefUpdateVO updateVO) {
        fieldRefService.update(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        fieldRefService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<FieldRefVO> get(@RequestParam("id") Long id) {
        return success(FieldRefConvert.INSTANCE.convert(fieldRefService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<FieldRefVO>> page(@Valid FieldRefPageVO pageVO) {
        return success(FieldRefConvert.INSTANCE.convert(fieldRefService.page(pageVO)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/list")
    public CommonResult<List<FieldRefVO>> list(@Valid FieldRefPageVO pageVO) {
        return success(FieldRefConvert.INSTANCE.convert(fieldRefService.list(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid FieldRefPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "FieldRefVO.xls", "数据", FieldRefVO.class, FieldRefConvert.INSTANCE.convert(fieldRefService.page(pageVO)).getList());
    }

    /**
     * 导入
     *
     * @param file 文件
     * @return 结果
     * @throws IOException IO异常
     */
    @PostMapping("/import")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<FieldRefVO> read = ExcelUtil.read(file, FieldRefVO.class);
        // do something
        return success(true);
    }
}
