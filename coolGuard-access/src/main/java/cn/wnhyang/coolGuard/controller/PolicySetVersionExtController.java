package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.PolicySetVersionExtConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetVersionExtService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.PolicySetVersionExtVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetVersionExtCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionExtPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetVersionExtUpdateVO;
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
 * 策略集版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Slf4j
@RestController
@RequestMapping("/policySetVersionExt")
@RequiredArgsConstructor
public class PolicySetVersionExtController {

    private final PolicySetVersionExtService policySetVersionExtService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid PolicySetVersionExtCreateVO createVO) {
        return success(policySetVersionExtService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid PolicySetVersionExtUpdateVO updateVO) {
        policySetVersionExtService.update(updateVO);
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
        policySetVersionExtService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<PolicySetVersionExtVO> get(@PathVariable("id") Long id) {
        return success(PolicySetVersionExtConvert.INSTANCE.convert(policySetVersionExtService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<PolicySetVersionExtVO>> page(@Valid PolicySetVersionExtPageVO pageVO) {
        return success(PolicySetVersionExtConvert.INSTANCE.convert(policySetVersionExtService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid PolicySetVersionExtPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "PolicySetVersionExtVO.xls", "数据", PolicySetVersionExtVO.class, PolicySetVersionExtConvert.INSTANCE.convert(policySetVersionExtService.page(pageVO)).getList());
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
        List<PolicySetVersionExtVO> read = ExcelUtil.read(file, PolicySetVersionExtVO.class);
        // do something
        return success(true);
    }
}
