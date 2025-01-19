package cn.wnhyang.coolGuard.system.controller;

import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.ParamConvert;
import cn.wnhyang.coolGuard.system.service.ParamService;
import cn.wnhyang.coolGuard.system.vo.param.ParamCreateVO;
import cn.wnhyang.coolGuard.system.vo.param.ParamPageVO;
import cn.wnhyang.coolGuard.system.vo.param.ParamUpdateVO;
import cn.wnhyang.coolGuard.system.vo.param.ParamVO;
import cn.wnhyang.coolGuard.util.ExcelUtil;
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
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Slf4j
@RestController
@RequestMapping("/system/param")
@RequiredArgsConstructor
public class ParamController {

    private final ParamService paramService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid ParamCreateVO createVO) {
        return success(paramService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid ParamUpdateVO updateVO) {
        paramService.update(updateVO);
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
        paramService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<ParamVO> get(@RequestParam("id") Long id) {
        return success(ParamConvert.INSTANCE.convert(paramService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ParamVO>> page(@Valid ParamPageVO pageVO) {
        return success(ParamConvert.INSTANCE.convert(paramService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid ParamPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "ParamVO.xls", "数据", ParamVO.class, ParamConvert.INSTANCE.convert(paramService.page(pageVO)).getList());
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
        List<ParamVO> read = ExcelUtil.read(file, ParamVO.class);
        // do something
        return success(true);
    }
}
