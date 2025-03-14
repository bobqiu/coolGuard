package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.system.service.PermissionService;
import cn.wnhyang.coolguard.system.vo.permission.RoleMenuVO;
import cn.wnhyang.coolguard.system.vo.permission.UserRoleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 权限
 *
 * @author wnhyang
 * @date 2023/11/13
 **/
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 给角色赋予菜单
     *
     * @param reqVO 请求对象，包含角色ID和菜单ID列表
     * @return 结果
     */
    @PostMapping("/roleMenu")
    @SaCheckPermission("system:permission:roleMenu")
    public CommonResult<Boolean> roleMenu(@RequestBody @Valid RoleMenuVO reqVO) {
        permissionService.roleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return CommonResult.success(true);
    }

    /**
     * 查询角色菜单列表
     *
     * @param roleId 角色ID
     * @return 角色对应的菜单ID集合
     */
    @GetMapping("/getRoleMenuList")
    @SaCheckPermission("system:permission:roleMenuList")
    public CommonResult<Set<Long>> getRoleMenuList(@RequestParam("roleId") Long roleId) {
        return CommonResult.success(permissionService.getMenuIdListByRoleId(roleId));
    }

    /**
     * 给用户赋予角色
     *
     * @param reqVO 请求对象，包含用户ID和角色ID列表
     * @return 结果
     */
    @PostMapping("/userRole")
    @SaCheckPermission("system:permission:userRole")
    public CommonResult<Boolean> roleMenu(@RequestBody @Valid UserRoleVO reqVO) {
        permissionService.userRole(reqVO.getUserId(), reqVO.getRoleIds());
        return CommonResult.success(true);
    }

    /**
     * 查询用户角色列表
     *
     * @param userId 用户ID
     * @return 用户对应的角色ID集合
     */
    @GetMapping("/getUserRoleList")
    @SaCheckPermission("system:permission:userRoleList")
    public CommonResult<Set<Long>> getUserRoleList(@RequestParam("userId") Long userId) {
        return CommonResult.success(permissionService.getRoleIdListByUserId(userId));
    }

}
