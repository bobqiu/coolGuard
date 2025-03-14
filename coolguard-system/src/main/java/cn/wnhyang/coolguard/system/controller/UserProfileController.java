package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.satoken.util.LoginUtil;
import cn.wnhyang.coolguard.system.convert.UserConvert;
import cn.wnhyang.coolguard.system.entity.UserDO;
import cn.wnhyang.coolguard.system.service.UserService;
import cn.wnhyang.coolguard.system.vo.userprofile.UserProfileUpdatePasswordVO;
import cn.wnhyang.coolguard.system.vo.userprofile.UserProfileUpdateVO;
import cn.wnhyang.coolguard.system.vo.userprofile.UserProfileVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户设置
 *
 * @author wnhyang
 * @date 2023/11/23
 **/
@RestController
@RequestMapping("/system/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    /**
     * 查询登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping
    @SaCheckLogin
    public CommonResult<UserProfileVO> getUserProfile() {
        UserDO userDO = userService.getUserById(LoginUtil.getUserId());

        return CommonResult.success(UserConvert.INSTANCE.convert04(userDO));
    }

    /**
     * 修改用户信息
     */
    @PostMapping
    @OperateLog(module = "后台-用户设置", name = "修改用户信息")
    @SaCheckLogin
    public CommonResult<Boolean> updateUserProfile(@RequestBody @Valid UserProfileUpdateVO reqVO) {
        userService.updateUserProfile(LoginUtil.getUserId(), reqVO);
        return CommonResult.success(true);
    }

    /**
     * 修改用户密码
     *
     * @param reqVO 新旧密码
     * @return 结果
     */
    @PutMapping("/updatePassword")
    @OperateLog(module = "后台-用户设置", name = "修改用户密码")
    @SaCheckLogin
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid UserProfileUpdatePasswordVO reqVO) {
        userService.updateUserPassword(LoginUtil.getUserId(), reqVO);
        return CommonResult.success(true);
    }


}
