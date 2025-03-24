package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.common.util.ExcelUtil;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import cn.wnhyang.coolguard.system.convert.UserConvert;
import cn.wnhyang.coolguard.system.entity.RoleDO;
import cn.wnhyang.coolguard.system.entity.UserDO;
import cn.wnhyang.coolguard.system.service.PermissionService;
import cn.wnhyang.coolguard.system.service.RoleService;
import cn.wnhyang.coolguard.system.service.UserService;
import cn.wnhyang.coolguard.system.vo.user.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

/**
 * 用户
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 创建用户
     *
     * @param reqVO 用户信息
     * @return 用户id
     */
    @PostMapping
    @OperateLog(module = "后台-用户", name = "创建用户", type = OperateType.CREATE)
    @SaCheckPermission("system:user:create")
    public CommonResult<Long> createUser(@RequestBody @Valid UserCreateVO reqVO) {
        Long id = userService.createUser(reqVO);
        return success(id);
    }

    /**
     * 修改用户信息
     *
     * @param reqVO 用户信息
     * @return 结果
     */
    @PutMapping
    @OperateLog(module = "后台-用户", name = "修改用户信息", type = OperateType.UPDATE)
    @SaCheckPermission("system:user:update")
    public CommonResult<Boolean> updateUser(@RequestBody @Valid UserUpdateVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 结果
     */
    @DeleteMapping
    @OperateLog(module = "后台-用户", name = "删除用户", type = OperateType.DELETE)
    @SaCheckPermission("system:user:delete")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    /**
     * 更新用户密码
     *
     * @param reqVO id+密码
     * @return 结果
     */
    @PutMapping("/updatePassword")
    @OperateLog(module = "后台-用户", name = "更新用户密码")
    @SaCheckPermission("system:user:updatePassword")
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid UserUpdatePasswordVO reqVO) {
        userService.updateUserPassword(reqVO);
        return success(true);
    }

    /**
     * 更新用户状态
     *
     * @param reqVO id+状态
     * @return 结果
     */
    @PutMapping("/updateStatus")
    @OperateLog(module = "后台-用户", name = "更新用户状态")
    @SaCheckPermission("system:user:update")
    public CommonResult<Boolean> updateUserStatus(@RequestBody @Valid UserUpdateStatusVO reqVO) {
        userService.updateUserStatus(reqVO);
        return success(true);
    }

    /**
     * 查询用户信息
     *
     * @param id id
     * @return 用户
     */
    @GetMapping
    @SaCheckPermission("system:user:query")
    public CommonResult<UserRespVO> getUser(@RequestParam("id") Long id) {
        UserDO userDO = userService.getUserById(id);
        Set<Long> roleIds = permissionService.getRoleIdListByUserId(userDO.getId());

        List<RoleDO> roleDOList = roleService.getRoleList(roleIds);
        UserRespVO respVO = UserConvert.INSTANCE.convert(userDO, roleDOList);
        respVO.setRoleIds(roleIds);

        return success(respVO);
    }

    /**
     * 查询用户信息列表
     *
     * @param reqVO 查询条件
     * @return 用户列表
     */
    @GetMapping("/page")
    @SaCheckPermission("system:user:list")
    public CommonResult<PageResult<UserRespVO>> getUserPage(@Valid UserPageVO reqVO) {
        List<UserRespVO> userPageList = getUserPageList(reqVO);

        return success(new PageResult<>(userPageList, (long) userPageList.size()));
    }

    private List<UserRespVO> getUserPageList(UserPageVO reqVO) {
        PageResult<UserDO> pageResult = userService.getUserPage(reqVO);

        return pageResult.getList().stream().map(user -> {
            Set<Long> roleIds = permissionService.getRoleIdListByUserId(user.getId());
            List<RoleDO> roleDOList = roleService.getRoleList(roleIds);
            UserRespVO respVO = UserConvert.INSTANCE.convert(user, roleDOList);
            respVO.setRoleIds(roleIds);
            respVO.setRoleList(CollectionUtils.convertList(roleDOList, RoleDO::getLabelValue));
            return respVO;
        }).collect(Collectors.toList());
    }

    /**
     * 导出用户信息
     *
     * @param exportReqVO 查询条件
     * @param response    响应
     * @throws IOException 响应异常
     */
    @GetMapping("/export")
    @OperateLog(module = "后台-用户", name = "导出用户列表")
    @SaCheckPermission("system:user:export")
    public void exportExcel(@Valid UserPageVO exportReqVO, HttpServletResponse response) throws IOException {
        List<UserRespVO> userPageList = getUserPageList(exportReqVO);
        // 输出 Excel
        ExcelUtil.write(response, "用户数据.xls", "数据", UserRespVO.class, userPageList);
    }

    /**
     * 导入用户信息
     *
     * @param file 文件
     * @return 结果
     * @throws IOException 响应异常
     */
    @PostMapping("/import")
    @OperateLog(module = "后台-用户", name = "导入用户")
    @SaCheckPermission("system:user:import")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserDO> read = ExcelUtil.read(file, UserDO.class);
        // do something
        log.info("导入用户信息：{}", read);
        return success(true);
    }

}
