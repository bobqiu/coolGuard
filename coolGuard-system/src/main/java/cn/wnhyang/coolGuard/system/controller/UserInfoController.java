package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.wnhyang.coolGuard.log.core.annotation.OperateLog;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.satoken.Login;
import cn.wnhyang.coolGuard.satoken.core.util.LoginUtil;
import cn.wnhyang.coolGuard.system.convert.UserConvert;
import cn.wnhyang.coolGuard.system.entity.UserPO;
import cn.wnhyang.coolGuard.system.service.MenuService;
import cn.wnhyang.coolGuard.system.service.UserService;
import cn.wnhyang.coolGuard.system.vo.user.UserInfoVO;
import cn.wnhyang.coolGuard.system.vo.core.user.UserInfoRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.GlobalErrorCode.UNAUTHORIZED;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * @author wnhyang
 * @date 2024/9/11
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    private final MenuService menuService;

    /**
     * 查询用户信息(登录成功后调用)
     *
     * @return 用户信息
     */
    @GetMapping("/info2")
    @OperateLog(module = "后台-用户", name = "查询用户信息")
    @SaCheckLogin
    public CommonResult<UserInfoVO> getUserInfo() {

        Login loginUser = LoginUtil.getLoginUser();

        if (loginUser == null) {
            throw exception(UNAUTHORIZED);
        }
        Long id = loginUser.getId();

        UserPO user = userService.getUserById(id);
        UserInfoVO respVO = new UserInfoVO();
        UserInfoVO.UserVO userVO = UserConvert.INSTANCE.convert03(user);
        respVO.setUser(userVO);
        respVO.setRoles(loginUser.getRoleValues());
        respVO.setPermissions(loginUser.getPermissions());

        List<UserInfoVO.MenuVO> userMenuTreeList = menuService.getLoginUserMenuTreeList(true);
        respVO.setMenus(userMenuTreeList);
        return success(respVO);
    }

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

        UserPO user = userService.getUserById(id);
        return CommonResult.success(new UserInfoRespVO()
                .setUserId(id)
                .setUsername(user.getUsername())
                .setAvatar(user.getAvatar())
                .setRealName(user.getNickname())
                .setDesc(user.getRemark())
                .setToken(StpUtil.getTokenValue())
                .setRoles(loginUser.getRoleValues()));
    }
}
