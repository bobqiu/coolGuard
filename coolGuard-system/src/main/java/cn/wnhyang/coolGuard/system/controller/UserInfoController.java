package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.satoken.Login;
import cn.wnhyang.coolGuard.satoken.util.LoginUtil;
import cn.wnhyang.coolGuard.system.entity.UserDO;
import cn.wnhyang.coolGuard.system.service.UserService;
import cn.wnhyang.coolGuard.system.vo.core.user.UserInfoRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.wnhyang.coolGuard.common.exception.GlobalErrorCode.UNAUTHORIZED;
import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 用户信息
 *
 * @author wnhyang
 * @date 2024/9/11
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    /**
     * 查询用户信息(登录成功后调用)
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    @OperateLog(module = "后台-用户", name = "查询用户信息")
    @SaCheckLogin
    public CommonResult<UserInfoRespVO> userInfo() {

        Login loginUser = LoginUtil.getLoginUser();

        if (loginUser == null) {
            throw exception(UNAUTHORIZED);
        }
        Long id = loginUser.getId();

        UserDO userDO = userService.getUserById(id);
        return CommonResult.success(new UserInfoRespVO()
                .setUserId(id)
                .setUsername(userDO.getUsername())
                .setAvatar(userDO.getAvatar())
                .setRealName(userDO.getNickname())
                .setDesc(userDO.getRemark())
                .setToken(StpUtil.getTokenValue())
                .setRoles(loginUser.getRoleValues()));
    }
}
