package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.log.core.annotation.OperateLog;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.DictDataConvert;
import cn.wnhyang.coolGuard.system.service.DictDataService;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataCreateVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataPageVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataRespVO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 字典数据
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@RestController
@RequestMapping("/system/dictData")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    /**
     * 创建字典数据
     *
     * @param reqVO 字典数据
     * @return 字典数据id
     */
    @PostMapping
    @OperateLog(module = "后台-字典", name = "创建字典数据")
    @SaCheckPermission("system:dict:create")
    public CommonResult<Long> createDictData(@Valid @RequestBody DictDataCreateVO reqVO) {
        Long dictDataId = dictDataService.createDictData(reqVO);
        return success(dictDataId);
    }

    /**
     * 更新字典数据
     *
     * @param reqVO 字典数据
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-字典", name = "更新字典数据")
    @SaCheckPermission("system:dict:update")
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return success(true);
    }

    /**
     * 删除字典数据
     *
     * @param id 字典数据id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-字典", name = "删除字典数据")
    @SaCheckPermission("system:dict:delete")
    public CommonResult<Boolean> deleteDictData(@RequestParam("id") Long id) {
        dictDataService.deleteDictData(id);
        return success(true);
    }

    /**
     * 查询简单菜单数据
     *
     * @return 菜单列表
     */
    @GetMapping("/list")
    @SaCheckLogin
    public CommonResult<List<DictDataRespVO>> getList(@RequestParam("type") String type) {
        return success(DictDataConvert.INSTANCE.convertList(dictDataService.getDictDataListByDictType(type)));
    }

    /**
     * 分页字典数据
     *
     * @param reqVO 分页请求
     * @return 字典数据
     */
    @GetMapping("/page")
    @OperateLog(module = "后台-字典", name = "分页字典数据")
    @SaCheckPermission("system:dict:query")
    public CommonResult<PageResult<DictDataRespVO>> getDictDataPage(@Valid DictDataPageVO reqVO) {
        return success(DictDataConvert.INSTANCE.convertPage(dictDataService.getDictDataPage(reqVO)));
    }

    /**
     * 查询详细字典数据
     *
     * @param id 字典数据id
     * @return 字典数据
     */
    @GetMapping
    @OperateLog(module = "后台-字典", name = "查询详细字典数据")
    @SaCheckPermission("system:dict:query")
    public CommonResult<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        return success(DictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
    }

}
