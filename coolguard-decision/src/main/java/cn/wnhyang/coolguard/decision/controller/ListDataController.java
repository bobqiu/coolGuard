package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.ExcelUtil;
import cn.wnhyang.coolguard.decision.convert.ListDataConvert;
import cn.wnhyang.coolguard.decision.service.ListDataService;
import cn.wnhyang.coolguard.decision.vo.ListDataVO;
import cn.wnhyang.coolguard.decision.vo.create.ListDataCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.ListDataPageVO;
import cn.wnhyang.coolguard.decision.vo.update.ListDataUpdateVO;
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
    @SaCheckPermission("decision:listData:create")
    @OperateLog(module = "后台-名单数据", name = "创建名单数据", type = OperateType.CREATE)
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
    @SaCheckPermission("decision:listData:update")
    @OperateLog(module = "后台-名单数据", name = "更新名单数据", type = OperateType.UPDATE)
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
    @SaCheckPermission("decision:listData:delete")
    @OperateLog(module = "后台-名单数据", name = "删除名单数据", type = OperateType.DELETE)
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
    @SaCheckLogin
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
    @SaCheckLogin
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
    @SaCheckPermission("decision:listData:export")
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
    @SaCheckPermission("decision:listData:import")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<ListDataVO> read = ExcelUtil.read(file, ListDataVO.class);
        // do something
        return success(true);
    }
}
