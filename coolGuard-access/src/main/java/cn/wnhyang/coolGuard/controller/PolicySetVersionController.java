package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.PolicySetVersionConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionService;
import cn.wnhyang.coolGuard.util.ExcelUtils;
import cn.wnhyang.coolGuard.vo.PolicySetVersionVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionUpdateVO;
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
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@RestController
@RequestMapping("/policySetVersion")
@RequiredArgsConstructor
public class PolicySetVersionController {

    private final PolicySetVersionService policySetVersionService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid PolicySetVersionCreateVO createVO) {
        return success(policySetVersionService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid PolicySetVersionUpdateVO updateVO) {
        policySetVersionService.update(updateVO);
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
        policySetVersionService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<PolicySetVersionVO> get(@PathVariable("id") Long id) {
        return success(PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.get(id)));
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
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid PolicySetVersionPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtils.write(response, "PolicySetVersionVO.xls", "数据", PolicySetVersionVO.class, PolicySetVersionConvert.INSTANCE.convert(policySetVersionService.page(pageVO)).getList());
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
        List<PolicySetVersionVO> read = ExcelUtils.read(file, PolicySetVersionVO.class);
        // do something
        return success(true);
    }
}
