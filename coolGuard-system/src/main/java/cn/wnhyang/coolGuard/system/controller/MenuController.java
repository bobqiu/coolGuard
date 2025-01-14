package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.log.core.annotation.OperateLog;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.system.convert.MenuConvert;
import cn.wnhyang.coolGuard.system.entity.MenuDO;
import cn.wnhyang.coolGuard.system.service.MenuService;
import cn.wnhyang.coolGuard.system.vo.menu.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;


/**
 * 菜单
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 创建菜单
     *
     * @param reqVO 菜单数据
     * @return 菜单id
     */
    @PostMapping
    @OperateLog(module = "后台-菜单", name = "创建菜单")
    @SaCheckPermission("system:menu:create")
    public CommonResult<Long> createMenu(@RequestBody @Valid MenuCreateVO reqVO) {
        return success(menuService.createMenu(reqVO));
    }

    /**
     * 更新菜单
     *
     * @param reqVO 菜单数据
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-菜单", name = "更新菜单")
    @SaCheckPermission("system:menu:update")
    public CommonResult<Boolean> updateMenu(@RequestBody @Valid MenuUpdateVO reqVO) {
        menuService.updateMenu(reqVO);
        return success(true);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-菜单", name = "删除菜单")
    @SaCheckPermission("system:menu:delete")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    /**
     * 查询菜单列表
     *
     * @param reqVO 菜单数据
     * @return 菜单列表
     */
    @GetMapping("/list")
    @OperateLog(module = "后台-菜单", name = "查询菜单列表")
    @SaCheckPermission("system:menu:list")
    public CommonResult<List<MenuRespVO>> getMenuList(@Valid MenuListVO reqVO) {
        return success(MenuConvert.INSTANCE.convertList(menuService.getMenuList(reqVO)));
    }

    /**
     * 查询菜单列表
     *
     * @param reqVO 菜单数据
     * @return 菜单列表
     */
    @GetMapping("/listTree")
    @OperateLog(module = "后台-菜单", name = "查询菜单列表")
    @SaCheckPermission("system:menu:list")
    public CommonResult<List<MenuTreeRespVO>> getMenuTreeList(@Valid MenuListVO reqVO) {
        return success(menuService.getMenuTreeList(reqVO));
    }

    /**
     * 查询菜单
     *
     * @param id 菜单id
     * @return 菜单
     */
    @GetMapping
    @SaCheckPermission("system:menu:query")
    public CommonResult<MenuRespVO> getMenu(@RequestParam("id") Long id) {
        MenuDO menuDO = menuService.getMenu(id);
        return success(MenuConvert.INSTANCE.convert2RespVO(menuDO));
    }

    /**
     * 查询简单菜单列表不带根节点
     *
     * @return 菜单列表
     */
    @GetMapping("/simpleList")
    @SaCheckLogin
    public CommonResult<List<MenuSimpleTreeVO>> getSimpleMenuList() {
        return success(menuService.getMenuSimpleTreeList());
    }

    /**
     * 查询简单菜单列表带根节点
     *
     * @return 菜单列表
     */
    @GetMapping("/simpleListA")
    @SaCheckLogin
    public CommonResult<List<MenuSimpleTreeVO>> getSimpleMenuListA() {
        return success(menuService.getMenuSimpleTreeListA());
    }
}
