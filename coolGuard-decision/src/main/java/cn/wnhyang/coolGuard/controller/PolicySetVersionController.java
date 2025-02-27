package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.PolicySetVersionConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.PolicySetVersionVO;
import cn.wnhyang.coolGuard.vo.base.IdBaseVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
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
     * @param id id
     * @return true/false
     */
    @PostMapping("/offline")
    public CommonResult<Boolean> offline(@RequestParam("id") Long id) {
        policySetVersionService.offline(id);
        return success(true);
    }

    /**
     * 选中
     *
     * @param idBaseVO idBaseVO
     * @return true/false
     */
    @PostMapping("/chose")
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
    public CommonResult<PolicySetVersionVO> get(@RequestParam("id") Long id) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.get(id)));
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<PolicySetVersionVO> getByCode(@RequestParam("code") String code) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.getByCode(code)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
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
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policySetVersionService.getLabelValueList());
    }
}
