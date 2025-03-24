package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.ExcelUtil;
import cn.wnhyang.coolguard.decision.convert.PolicySetVersionConvert;
import cn.wnhyang.coolguard.decision.service.PolicySetVersionService;
import cn.wnhyang.coolguard.decision.vo.PolicySetVersionVO;
import cn.wnhyang.coolguard.decision.vo.base.IdBaseVO;
import cn.wnhyang.coolguard.decision.vo.page.PolicySetVersionPageVO;
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
 * 策略集版本
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Slf4j
@RestController
@RequestMapping("/policySetVersion")
@RequiredArgsConstructor
public class PolicySetVersionController {

    private final PolicySetVersionService policySetVersionService;

    /**
     * 下线
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/offline")
    @SaCheckPermission("decision:policySet:offline")
    @OperateLog(module = "后台-策略集", name = "下线策略集", type = OperateType.DELETE)
    public CommonResult<Boolean> offline(@RequestBody IdBaseVO idBaseVO) {
        policySetVersionService.offline(idBaseVO.getId());
        return success(true);
    }

    /**
     * 选中
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/chose")
    @SaCheckPermission("decision:policySet:chose")
    @OperateLog(module = "后台-策略集", name = "选中策略集", type = OperateType.UPDATE)
    public CommonResult<Boolean> chose(@RequestBody IdBaseVO idBaseVO) {
        policySetVersionService.chose(idBaseVO.getId());
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    @SaCheckLogin
    public CommonResult<PolicySetVersionVO> get(@RequestParam("id") Long id) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.get(id)));
    }

    /**
     * 根据code和version查询
     *
     * @param queryVO queryVO
     * @return vo
     */
    @GetMapping("/cv")
    @SaCheckLogin
    public CommonResult<PolicySetVersionVO> getByCv(@Valid CvQueryVO queryVO) {
        return success(policySetVersionService.getByCv(queryVO));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    @SaCheckLogin
    public CommonResult<List<PolicySetVersionVO>> getByCode(@RequestParam("code") String code) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.getByCode(code)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    @SaCheckLogin
    public CommonResult<PageResult<PolicySetVersionVO>> page(@Valid PolicySetVersionPageVO pageVO) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.page(pageVO)));
    }

    /**
     * 根据code分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/pageByCode")
    @SaCheckLogin
    public CommonResult<PageResult<PolicySetVersionVO>> pageByCode(@Valid PolicySetVersionPageVO pageVO) {
        return success(policySetVersionService.pageByCode(pageVO));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    @SaCheckPermission("decision:policySet:export")
    public void exportExcel(@Valid PolicySetVersionPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "PolicySetVersionVO.xls", "数据", PolicySetVersionVO.class, PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.page(pageVO)).getList());
    }

    /**
     * 导入
     *
     * @param file 文件
     * @return 结果
     * @throws IOException IO异常
     */
    @PostMapping("/import")
    @SaCheckPermission("decision:policySet:import")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<PolicySetVersionVO> read = ExcelUtil.read(file, PolicySetVersionVO.class);
        // do something
        return success(true);
    }

    /**
     * 获取策略集lv列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policySetVersionService.getLabelValueList());
    }
}
