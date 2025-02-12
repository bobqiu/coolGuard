package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.RuleVersionConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleVersionService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.RuleVersionVO;
import cn.wnhyang.coolGuard.vo.page.RuleVersionPageVO;
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
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@RestController
@RequestMapping("/policyVersionExt")
@RequiredArgsConstructor
public class RuleVersionController {

    private final RuleVersionService ruleVersionService;

    /**
     * 下线
     *
     * @param id id
     * @return true/false
     */
    @PostMapping("/offline")
    public CommonResult<Boolean> offline(@RequestParam("id") Long id) {
        ruleVersionService.offline(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<RuleVersionVO> get(@RequestParam("id") Long id) {
        return success(RuleVersionConvert.INSTANCE.convert(ruleVersionService.get(id)));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<RuleVersionVO> getByCode(@RequestParam("code") String code) {
        return success(RuleVersionConvert.INSTANCE.convert(ruleVersionService.getByCode(code)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<RuleVersionVO>> page(@Valid RuleVersionPageVO pageVO) {
        return success(RuleVersionConvert.INSTANCE.convert(ruleVersionService.page(pageVO)));
    }

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/pageByCode")
    public CommonResult<PageResult<RuleVersionVO>> pageByCode(@Valid RuleVersionPageVO pageVO) {
        return success(ruleVersionService.pageByCode(pageVO));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid RuleVersionPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "PolicySetVersionExtVO.xls", "数据", RuleVersionVO.class, RuleVersionConvert.INSTANCE.convert(ruleVersionService.page(pageVO)).getList());
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
        List<RuleVersionVO> read = ExcelUtil.read(file, RuleVersionVO.class);
        // do something
        return success(true);
    }
}
