package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ListDataConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ListDataService;
import cn.wnhyang.coolGuard.util.ExcelUtil;
import cn.wnhyang.coolGuard.vo.ListDataVO;
import cn.wnhyang.coolGuard.vo.create.ListDataCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListDataPageVO;
import cn.wnhyang.coolGuard.vo.update.ListDataUpdateVO;
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
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Slf4j
@RestController
@RequestMapping("/listData")
@RequiredArgsConstructor
public class ListDataController {

    private final ListDataService listDataService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> create(@RequestBody @Valid ListDataCreateVO createVO) {
        return success(listDataService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody @Valid ListDataUpdateVO updateVO) {
        listDataService.update(updateVO);
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
        listDataService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<ListDataVO> get(@RequestParam("id") Long id) {
        return success(ListDataConvert.INSTANCE.convert(listDataService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ListDataVO>> page(@Valid ListDataPageVO pageVO) {
        return success(ListDataConvert.INSTANCE.convert(listDataService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid ListDataPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "ListDataVO.xls", "数据", ListDataVO.class, ListDataConvert.INSTANCE.convert(listDataService.page(pageVO)).getList());
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
        List<ListDataVO> read = ExcelUtil.read(file, ListDataVO.class);
        // do something
        return success(true);
    }
}
