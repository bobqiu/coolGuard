package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.system.service.MenuService;
import cn.wnhyang.coolguard.system.vo.menu.MenuTreeRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 菜单路由
 *
 * @author wnhyang
 * @date 2025/1/5
 **/
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuRouteController {

    private final MenuService menuService;

    /**
     * 查询当前登录用户菜单列表
     * 前端动态权限
     * <a href="https://doc.vben.pro/guide/in-depth/access.html#%E5%90%8E%E7%AB%AF%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6">前端动态权限</a>
     *
     * @return 菜单列表
     */
    @GetMapping("/all")
    @SaCheckLogin
    public CommonResult<List<MenuTreeRespVO>> getAllMenuList() {
        return success(menuService.getLoginUserMenuWithRouteMetaTreeList(true));
    }
}
