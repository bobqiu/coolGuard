package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.log.core.annotation.OperateLog;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.RoleConvert;
import cn.wnhyang.coolGuard.system.dto.RoleSimpleVO;
import cn.wnhyang.coolGuard.system.entity.RolePO;
import cn.wnhyang.coolGuard.system.service.PermissionService;
import cn.wnhyang.coolGuard.system.service.RoleService;
import cn.wnhyang.coolGuard.system.vo.role.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 角色
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 创建角色
     *
     * @param reqVO 角色信息
     * @return id
     */
    @PostMapping
    @OperateLog(module = "后台-角色", name = "创建角色")
    @SaCheckPermission("system:role:create")
    public CommonResult<Long> createRole(@Valid @RequestBody RoleCreateVO reqVO) {
        return success(roleService.createRole(reqVO));
    }

    /**
     * 更新角色
     *
     * @param reqVO 角色信息
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-角色", name = "更新角色")
    @SaCheckPermission("system:role:update")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateVO reqVO) {
        roleService.updateRole(reqVO);
        return success(true);
    }

    /**
     * 更新角色状态
     *
     * @param reqVO id+状态
     * @return 结果
     */
    @PutMapping("/updateStatus")
    @OperateLog(module = "后台-角色", name = "更新角色状态")
    @SaCheckPermission("system:role:update")
    public CommonResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-角色", name = "删除角色")
    @SaCheckPermission("system:role:delete")
    public CommonResult<Boolean> deleteRole(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return success(true);
    }

    /**
     * 查询角色信息
     *
     * @param id id
     * @return 角色信息
     */
    @GetMapping
    @OperateLog(module = "后台-角色", name = "查询角色")
    @SaCheckPermission("system:role:query")
    public CommonResult<RoleRespVO> getRole(@RequestParam("id") Long id) {
        RolePO role = roleService.getRole(id);
        Set<Long> menuIds = permissionService.getMenuIdListByRoleId(role.getId());
        RoleRespVO respVO = RoleConvert.INSTANCE.convert(role);
        respVO.setMenuIds(menuIds);
        return success(respVO);
    }

    /**
     * 查询角色列表
     *
     * @param reqVO 请求数据
     * @return 角色列表
     */
    @GetMapping("/page")
    @OperateLog(module = "后台-角色", name = "查询角色列表")
    @SaCheckPermission("system:role:list")
    public CommonResult<PageResult<RoleRespVO>> getRolePage(@Valid RolePageVO reqVO) {
        PageResult<RolePO> pageResult = roleService.getRolePage(reqVO);
        List<RoleRespVO> roleRespVOList = pageResult.getList().stream().map(role -> {
            Set<Long> menuIds = permissionService.getMenuIdListByRoleId(role.getId());
            RoleRespVO respVO = RoleConvert.INSTANCE.convert(role);
            respVO.setMenuIds(menuIds);
            return respVO;
        }).collect(Collectors.toList());

        return success(new PageResult<>(roleRespVOList, pageResult.getTotal()));
    }

    /**
     * 查询简单角色列表
     */
    @GetMapping("/simpleList")
    @OperateLog(module = "后台-角色", name = "查询简单角色列表")
    @SaCheckLogin
    public CommonResult<List<RoleSimpleVO>> getSimpleRoleList() {
        return success(RoleConvert.INSTANCE.convert02(roleService.getRoleList(Boolean.TRUE)));
    }
}
