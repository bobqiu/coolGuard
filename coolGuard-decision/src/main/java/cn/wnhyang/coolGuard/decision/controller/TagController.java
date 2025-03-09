package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.ExcelUtil;
import cn.wnhyang.coolGuard.decision.convert.TagConvert;
import cn.wnhyang.coolGuard.decision.service.TagService;
import cn.wnhyang.coolGuard.decision.vo.TagVO;
import cn.wnhyang.coolGuard.decision.vo.create.TagCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.TagPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.TagUpdateVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;


/**
 * 标签表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Slf4j
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:tag:create")
    @OperateLog(module = "后台-标签", name = "创建标签", type = OperateType.CREATE)
    public CommonResult<Long> create(@RequestBody @Valid TagCreateVO createVO) {
        return success(tagService.create(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    @SaCheckPermission("decision:tag:update")
    @OperateLog(module = "后台-标签", name = "更新标签", type = OperateType.UPDATE)
    public CommonResult<Boolean> update(@RequestBody @Valid TagUpdateVO updateVO) {
        tagService.update(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    @SaCheckPermission("decision:tag:delete")
    @OperateLog(module = "后台-标签", name = "删除标签", type = OperateType.DELETE)
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        tagService.delete(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<TagVO> get(@RequestParam("id") Long id) {
        return success(TagConvert.INSTANCE.convert(tagService.get(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<TagVO>> page(@Valid TagPageVO pageVO) {
        return success(TagConvert.INSTANCE.convert(tagService.page(pageVO)));
    }

    /**
     * 导出
     *
     * @param pageVO   导出VO
     * @param response response
     * @throws IOException IO异常
     */
    @GetMapping("/export")
    public void exportExcel(@Valid TagPageVO pageVO, HttpServletResponse response) throws IOException {
        // 输出 Excel
        ExcelUtil.write(response, "TagVO.xls", "数据", TagVO.class, TagConvert.INSTANCE.convert(tagService.page(pageVO)).getList());
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
        List<TagVO> read = ExcelUtil.read(file, TagVO.class);
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
        return success(tagService.getLabelValueList());
    }
}
