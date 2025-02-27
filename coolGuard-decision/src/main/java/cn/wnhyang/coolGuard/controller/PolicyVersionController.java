package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.PolicyVersionConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyVersionService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.PolicyVersionVO;
import cn.wnhyang.coolGuard.vo.base.IdBaseVO;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionPageVO;
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
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        policyVersionService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
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
    public CommonResult<PageResult<PolicyVersionVO>> pageByCode(@Valid PolicyVersionPageVO pageVO) {
        return success(policyVersionService.pageByCode(pageVO));
    }

    /**
     * 下线
     *
     * @param id id
     * @return true/false
     */
    @PostMapping("/offline")
    public CommonResult<Boolean> offline(@RequestParam("id") Long id) {
        policyVersionService.offline(id);
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
        policyVersionService.chose(idBaseVO.getId());
        return success(true);
    }

    /**
     * 根据code查询
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<PolicyVersionVO> getByCode(@RequestParam("code") String code) {
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
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policyVersionService.getLabelValueList());
    }
}
