package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.ExcelUtil;
import cn.wnhyang.coolguard.decision.convert.RuleVersionConvert;
import cn.wnhyang.coolguard.decision.service.RuleVersionService;
import cn.wnhyang.coolguard.decision.vo.RuleVersionVO;
import cn.wnhyang.coolguard.decision.vo.base.IdBaseVO;
import cn.wnhyang.coolguard.decision.vo.page.RuleVersionPageVO;
import cn.wnhyang.coolguard.decision.vo.query.CvQueryVO;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@RestController
@RequestMapping("/ruleVersion")
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
    @SaCheckPermission("decision:rule:offline")
    @OperateLog(module = "后台-规则", name = "下线规则", type = OperateType.DELETE)
    public CommonResult<Boolean> offline(@RequestParam("id") Long id) {
        ruleVersionService.offline(id);
        return success(true);
    }

    /**
     * 选中
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/chose")
    @SaCheckPermission("decision:rule:chose")
    @OperateLog(module = "后台-规则", name = "选中规则", type = OperateType.UPDATE)
    public CommonResult<Boolean> chose(@RequestBody IdBaseVO idBaseVO) {
        ruleVersionService.chose(idBaseVO.getId());
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
     * 根据code和version查询
     *
     * @param queryVO queryVO
     * @return vo
     */
    @GetMapping("/cv")
    public CommonResult<RuleVersionVO> getByCv(@Valid CvQueryVO queryVO) {
        return success(ruleVersionService.getByCv(queryVO));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<List<RuleVersionVO>> getByCode(@RequestParam("code") String code) {
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

    /**
     * 获取规则lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(ruleVersionService.getLabelValueList());
    }
}
