package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import cn.wnhyang.coolGuard.system.convert.RoleConvert;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.service.PermissionService;
import cn.wnhyang.coolGuard.system.service.RoleService;
import cn.wnhyang.coolGuard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleRespVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;

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
    @OperateLog(module = "后台-角色", name = "创建角色", type = OperateType.CREATE)
    @SaCheckPermission("system:role:create")
    public CommonResult<Long> createRole(@RequestBody @Valid RoleCreateVO reqVO) {
        return success(roleService.createRole(reqVO));
    }

    /**
     * 更新角色
     *
     * @param reqVO 角色信息
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-角色", name = "更新角色", type = OperateType.UPDATE)
    @SaCheckPermission("system:role:update")
    public CommonResult<Boolean> updateRole(@RequestBody @Valid RoleUpdateVO reqVO) {
        roleService.updateRole(reqVO);
        return success(true);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-角色", name = "删除角色", type = OperateType.DELETE)
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
        RoleDO roleDO = roleService.getRole(id);
        Set<Long> menuIds = permissionService.getMenuIdListByRoleId(roleDO.getId());
        RoleRespVO respVO = RoleConvert.INSTANCE.convert(roleDO);
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
        PageResult<RoleDO> pageResult = roleService.getRolePage(reqVO);
        List<RoleRespVO> roleRespVOList = pageResult.getList().stream().map(role -> {
            Set<Long> menuIds = permissionService.getMenuIdListByRoleId(role.getId());
            RoleRespVO respVO = RoleConvert.INSTANCE.convert(role);
            respVO.setMenuIds(menuIds);
            return respVO;
        }).collect(Collectors.toList());

        return success(new PageResult<>(roleRespVOList, pageResult.getTotal()));
    }

    /**
     * 获取角色lv列表
     *
     * @return 角色lv列表
     */
    @GetMapping("/lvList")
    @SaCheckLogin
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(roleService.getLabelValueList());
    }
}
