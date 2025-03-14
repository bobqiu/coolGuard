package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.satoken.Login;
import cn.wnhyang.coolguard.satoken.util.LoginUtil;
import cn.wnhyang.coolguard.system.entity.UserDO;
import cn.wnhyang.coolguard.system.service.UserService;
import cn.wnhyang.coolguard.system.vo.core.user.UserInfoRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.wnhyang.coolguard.common.exception.GlobalErrorCode.UNAUTHORIZED;
import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;

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
