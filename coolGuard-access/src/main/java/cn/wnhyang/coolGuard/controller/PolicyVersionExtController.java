package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.PolicyVersionExtConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyVersionExtService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.PolicyVersionExtVO;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionExtPageVO;
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
 * 策略版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@RestController
@RequestMapping("/policyVersionExt")
@RequiredArgsConstructor
public class PolicyVersionExtController {

    private final PolicyVersionExtService policyVersionExtService;

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        policyVersionExtService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<PolicyVersionExtVO> get(@PathVariable("id") Long id) {
        return success(PolicyVersionExtConvert.INSTANCE.convert(policyVersionExtService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<PolicyVersionExtVO>> page(@Valid PolicyVersionExtPageVO pageVO) {
        return success(PolicyVersionExtConvert.INSTANCE.convert(policyVersionExtService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid PolicyVersionExtPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "PolicySetVersionExtVO.xls", "数据", PolicyVersionExtVO.class, PolicyVersionExtConvert.INSTANCE.convert(policyVersionExtService.page(pageVO)).getList());
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
        List<PolicyVersionExtVO> read = ExcelUtil.read(file, PolicyVersionExtVO.class);
        // do something
        return success(true);
    }
}
