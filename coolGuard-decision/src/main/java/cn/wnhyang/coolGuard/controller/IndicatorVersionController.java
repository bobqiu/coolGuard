package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorVersionService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.IndicatorVersionVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorVersionPageVO;
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
 * 指标版本表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Slf4j
@RestController
@RequestMapping("/indicatorVersion")
@RequiredArgsConstructor
public class IndicatorVersionController {

    private final IndicatorVersionService indicatorVersionService;

    /**
     * 下线
     *
     * @param id id
     * @return true/false
     */
    @PostMapping("/offline")
    public CommonResult<Boolean> offline(@RequestParam("id") Long id) {
        indicatorVersionService.offline(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<IndicatorVersionVO> get(@RequestParam("id") Long id) {
        return success(indicatorVersionService.get(id));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<IndicatorVersionVO> getByCode(@RequestParam("code") String code) {
        return success(indicatorVersionService.getByCode(code));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<IndicatorVersionVO>> page(@Valid IndicatorVersionPageVO pageVO) {
        return success(indicatorVersionService.page(pageVO));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid IndicatorVersionPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "IndicatorVersionVO.xls", "数据", IndicatorVersionVO.class, indicatorVersionService.page(pageVO).getList());
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
        List<IndicatorVersionVO> read = ExcelUtil.read(file, IndicatorVersionVO.class);
        // do something
        return success(true);
    }
}
