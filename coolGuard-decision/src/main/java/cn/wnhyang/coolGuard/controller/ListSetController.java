package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ListSetConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ListSetService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.ListSetVO;
import cn.wnhyang.coolGuard.vo.create.ListSetCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListSetPageVO;
import cn.wnhyang.coolGuard.vo.update.ListSetUpdateVO;
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
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Slf4j
@RestController
@RequestMapping("/listSet")
@RequiredArgsConstructor
public class ListSetController {

    private final ListSetService listSetService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid ListSetCreateVO createVO) {
        return success(listSetService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid ListSetUpdateVO updateVO) {
        listSetService.update(updateVO);
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
        listSetService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<ListSetVO> get(@RequestParam("id") Long id) {
        return success(ListSetConvert.INSTANCE.convert(listSetService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ListSetVO>> page(@Valid ListSetPageVO pageVO) {
        return success(ListSetConvert.INSTANCE.convert(listSetService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid ListSetPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "ListSetVO.xls", "数据", ListSetVO.class, ListSetConvert.INSTANCE.convert(listSetService.page(pageVO)).getList());
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
        List<ListSetVO> read = ExcelUtil.read(file, ListSetVO.class);
        // do something
        return success(true);
    }

    /**
     * 获取lvList
     *
     * @return lvList
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(listSetService.getLabelValueList());
    }
}
