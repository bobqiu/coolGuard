package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.ExcelUtil;
import cn.wnhyang.coolGuard.decision.service.IndicatorVersionService;
import cn.wnhyang.coolGuard.decision.vo.IndicatorSimpleVO;
import cn.wnhyang.coolGuard.decision.vo.IndicatorVersionVO;
import cn.wnhyang.coolGuard.decision.vo.base.IdBaseVO;
import cn.wnhyang.coolGuard.decision.vo.page.IndicatorVersionPageVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;


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
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/offline")
    @SaCheckPermission("decision:indicator:offline")
    @OperateLog(module = "后台-指标", name = "下线指标", type = OperateType.UPDATE)
    public CommonResult<Boolean> offline(@RequestBody IdBaseVO idBaseVO) {
        indicatorVersionService.offline(idBaseVO.getId());
        return success(true);
    }

    /**
     * 选中
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/chose")
    @SaCheckPermission("decision:indicator:chose")
    @OperateLog(module = "后台-指标版本", name = "选中指标版本", type = OperateType.UPDATE)
    public CommonResult<Boolean> chose(@RequestBody IdBaseVO idBaseVO) {
        indicatorVersionService.chose(idBaseVO.getId());
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
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/pageByCode")
    public CommonResult<PageResult<IndicatorVersionVO>> pageByCode(@Valid IndicatorVersionPageVO pageVO) {
        return success(indicatorVersionService.pageByCode(pageVO));
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

    /**
     * 获取lvList
     *
     * @return lvList
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(indicatorVersionService.getLabelValueList());
    }

    /**
     * 获取简单指标list
     *
     * @return 简单指标list
     */
    @GetMapping("/simpleList")
    public CommonResult<List<IndicatorSimpleVO>> getSimpleLabelValueList() {
        return success(indicatorVersionService.getSimpleList());
    }
}
