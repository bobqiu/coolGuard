package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.ExcelUtil;
import cn.wnhyang.coolguard.decision.convert.PolicyVersionConvert;
import cn.wnhyang.coolguard.decision.service.PolicyVersionService;
import cn.wnhyang.coolguard.decision.vo.PolicyVersionVO;
import cn.wnhyang.coolguard.decision.vo.base.IdBaseVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicyVersionPageVO;
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
 * 策略版本表
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Slf4j
@RestController
@RequestMapping("/policyVersion")
@RequiredArgsConstructor
public class PolicyVersionController {

    private final PolicyVersionService policyVersionService;

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    @SaCheckLogin
    public CommonResult<PolicyVersionVO> get(@RequestParam("id") Long id) {
        return success(PolicyVersionConvert.INSTANCE.convert(policyVersionService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    @SaCheckLogin
    public CommonResult<PageResult<PolicyVersionVO>> page(@Valid PolicyVersionPageVO pageVO) {
        return success(PolicyVersionConvert.INSTANCE.convert(policyVersionService.page(pageVO)));
    }

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/pageByCode")
    @SaCheckLogin
    public CommonResult<PageResult<PolicyVersionVO>> pageByCode(@Valid PolicyVersionPageVO pageVO) {
        return success(policyVersionService.pageByCode(pageVO));
    }

    /**
     * 下线
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/offline")
    @SaCheckPermission("decision:policy:offline")
    @OperateLog(module = "后台-策略", name = "下线策略", type = OperateType.DELETE)
    public CommonResult<Boolean> offline(@RequestBody IdBaseVO idBaseVO) {
        policyVersionService.offline(idBaseVO.getId());
        return success(true);
    }

    /**
     * 选中
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/chose")
    @SaCheckPermission("decision:policy:chose")
    @OperateLog(module = "后台-策略", name = "选中策略", type = OperateType.UPDATE)
    public CommonResult<Boolean> chose(@RequestBody IdBaseVO idBaseVO) {
        policyVersionService.chose(idBaseVO.getId());
        return success(true);
    }

    /**
     * 根据code和version查询
     *
     * @param queryVO queryVO
     * @return vo
     */
    @GetMapping("/cv")
    @SaCheckLogin
    public CommonResult<PolicyVersionVO> getByCv(@Valid CvQueryVO queryVO) {
        return success(policyVersionService.getByCv(queryVO));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    @SaCheckLogin
    public CommonResult<List<PolicyVersionVO>> getByCode(@RequestParam("code") String code) {
        return success(PolicyVersionConvert.INSTANCE.convert(policyVersionService.getByCode(code)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    @SaCheckPermission("decision:policy:export")
    public void exportExcel(@Valid PolicyVersionPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "PolicyVersionVO.xls", "数据", PolicyVersionVO.class, PolicyVersionConvert.INSTANCE.convert(policyVersionService.page(pageVO)).getList());
    }

    /**
     * 导入
     *
     * @param file 文件
     * @return 结果
     * @throws IOException IO异常
     */
    @PostMapping("/import")
    @SaCheckPermission("decision:policy:import")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<PolicyVersionVO> read = ExcelUtil.read(file, PolicyVersionVO.class);
        // do something
        return success(true);
    }

    /**
     * 获取策略lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policyVersionService.getLabelValueList());
    }
}
