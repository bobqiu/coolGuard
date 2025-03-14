package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import cn.wnhyang.coolguard.system.convert.DictTypeConvert;
import cn.wnhyang.coolguard.system.service.DictTypeService;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeCreateVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypePageVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeRespVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 字典类型
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@RestController
@RequestMapping("/system/dictType")
@RequiredArgsConstructor
public class DictTypeController {

    private final DictTypeService dictTypeService;

    /**
     * 新增字典类型
     *
     * @param reqVO 字典类型
     * @return 字典id
     */
    @PostMapping
    @OperateLog(module = "后台-字典", name = "创建字典类型", type = OperateType.CREATE)
    @SaCheckPermission("system:dict:create")
    public CommonResult<Long> createDictType(@Valid @RequestBody DictTypeCreateVO reqVO) {
        Long dictTypeId = dictTypeService.createDictType(reqVO);
        return success(dictTypeId);
    }

    /**
     * 更新字典
     *
     * @param reqVO 字典数据
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-字典", name = "更新字典类型", type = OperateType.UPDATE)
    @SaCheckPermission("system:dict:update")
    public CommonResult<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return success(true);
    }

    /**
     * 删除字典类型
     *
     * @param id 字典类型id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-字典", name = "删除字典类型", type = OperateType.DELETE)
    @SaCheckPermission("system:dict:delete")
    public CommonResult<Boolean> deleteDictType(@RequestParam("id") Long id) {
        dictTypeService.deleteDictType(id);
        return success(true);
    }

    /**
     * 分页字典类型
     *
     * @param reqVO 分页请求
     * @return 分页字典类型
     */
    @GetMapping("/page")
    @SaCheckPermission("system:dict:query")
    public CommonResult<PageResult<DictTypeRespVO>> pageDictType(@Valid DictTypePageVO reqVO) {
        return success(DictTypeConvert.INSTANCE.convertPage(dictTypeService.getDictTypePage(reqVO)));
    }

    /**
     * 查询字典类型详情
     *
     * @param id 字典类型id
     * @return 字典详情
     */
    @GetMapping
    @SaCheckPermission("system:dict:query")
    public CommonResult<DictTypeRespVO> getDictType(@RequestParam("id") Long id) {
        return success(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictType(id)));
    }

    /**
     * 获取字典lv列表
     *
     * @return list
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(dictTypeService.getLabelValueList());
    }
}
