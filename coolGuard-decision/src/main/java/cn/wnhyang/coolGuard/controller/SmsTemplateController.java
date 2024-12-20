package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.SmsTemplateConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.SmsTemplateService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.SmsTemplateVO;
import cn.wnhyang.coolGuard.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolGuard.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolGuard.vo.update.SmsTemplateUpdateVO;
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
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Slf4j
@RestController
@RequestMapping("/smsTemplate")
@RequiredArgsConstructor
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid SmsTemplateCreateVO createVO) {
        return success(smsTemplateService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid SmsTemplateUpdateVO updateVO) {
        smsTemplateService.update(updateVO);
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
        smsTemplateService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<SmsTemplateVO> get(@PathVariable("id") Long id) {
        return success(SmsTemplateConvert.INSTANCE.convert(smsTemplateService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SmsTemplateVO>> page(@Valid SmsTemplatePageVO pageVO) {
        return success(SmsTemplateConvert.INSTANCE.convert(smsTemplateService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid SmsTemplatePageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "SmsTemplateVO.xls", "数据", SmsTemplateVO.class, SmsTemplateConvert.INSTANCE.convert(smsTemplateService.page(pageVO)).getList());
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
        List<SmsTemplateVO> read = ExcelUtil.read(file, SmsTemplateVO.class);
        // do something
        return success(true);
    }
}
