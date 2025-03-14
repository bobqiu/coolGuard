package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import cn.wnhyang.coolguard.system.convert.DictDataConvert;
import cn.wnhyang.coolguard.system.service.DictDataService;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataCreateVO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataPageVO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataRespVO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

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
    @OperateLog(module = "后台-字典", name = "创建字典数据", type = OperateType.CREATE)
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
    @OperateLog(module = "后台-字典", name = "更新字典数据", type = OperateType.UPDATE)
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
    @OperateLog(module = "后台-字典", name = "删除字典数据", type = OperateType.DELETE)
    @SaCheckPermission("system:dict:delete")
    public CommonResult<Boolean> deleteDictData(@RequestParam("id") Long id) {
        dictDataService.deleteDictData(id);
        return success(true);
    }

    /**
     * 查询简单字典数据
     *
     * @return 字典列表
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
    @SaCheckPermission("system:dict:query")
    public CommonResult<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        return success(DictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
    }

}
