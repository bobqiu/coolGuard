package cn.wnhyang.coolGuard.system.controller;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.DictConvert;
import cn.wnhyang.coolGuard.system.entity.DictData;
import cn.wnhyang.coolGuard.system.service.DictService;
import cn.wnhyang.coolGuard.system.vo.dict.DictCreateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictPageVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictUpdateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictVO;
import cn.wnhyang.coolGuard.util.ExcelUtil;
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
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid DictCreateVO createVO) {
        return success(dictService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid DictUpdateVO updateVO) {
        dictService.update(updateVO);
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
        dictService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<DictVO> get(@PathVariable("id") Long id) {
        return success(DictConvert.INSTANCE.convert(dictService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<DictVO>> page(@Valid DictPageVO pageVO) {
        return success(DictConvert.INSTANCE.convert(dictService.page(pageVO)));
    }

    /**
     * 获取字典列表
     *
     * @return list
     */
    @GetMapping("lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(dictService.getLabelValueList());
    }

    /**
     * 获取字典数据
     *
     * @param value 字典值
     * @return 字典数据
     */
    @GetMapping("/dataList")
    public CommonResult<List<DictData>> getDataList(@RequestParam("value") String value) {
        return success(dictService.getDataList(value));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid DictPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "DictVO.xls", "数据", DictVO.class, DictConvert.INSTANCE.convert(dictService.page(pageVO)).getList());
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
        List<DictVO> read = ExcelUtil.read(file, DictVO.class);
        // do something
        return success(true);
    }
}
