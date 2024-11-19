package cn.wnhyang.coolGuard.system.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.enums.DeviceType;
import cn.wnhyang.coolGuard.enums.UserType;
import cn.wnhyang.coolGuard.satoken.Login;
import cn.wnhyang.coolGuard.satoken.core.util.LoginUtil;
import cn.wnhyang.coolGuard.system.dto.LoginLogCreateDTO;
import cn.wnhyang.coolGuard.system.dto.UserCreateDTO;
import cn.wnhyang.coolGuard.system.enums.login.LoginResult;
import cn.wnhyang.coolGuard.system.enums.login.LoginType;
import cn.wnhyang.coolGuard.system.login.LoginUser;
import cn.wnhyang.coolGuard.system.redis.RedisKey;
import cn.wnhyang.coolGuard.system.vo.core.auth.EmailLoginVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.LoginReqVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.LoginRespVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.RegisterVO;
import cn.wnhyang.coolGuard.util.RegexUtil;
import cn.wnhyang.coolGuard.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static cn.wnhyang.coolGuard.exception.GlobalErrorCode.UNAUTHORIZED;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.enums.ErrorCodes.*;


/**
 * @author wnhyang
 * @date 2023/7/26
 **/
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final LoginLogService loginLogService;

    private final ValueOperations<String, String> valueOperations;

    public LoginRespVO login(LoginReqVO reqVO) {
        String account = reqVO.getUsername();
        LoginUser user;
        LoginType loginType;
        if (StrUtil.isNotEmpty(account)) {
            if (ReUtil.isMatch(RegexUtil.MOBILE, account)) {
                user = userService.getLoginUser(account, account, "");
                loginType = LoginType.LOGIN_MOBILE;
            } else if (ReUtil.isMatch(RegexUtil.EMAIL, account)) {
                user = userService.getLoginUser(account, "", account);
                loginType = LoginType.LOGIN_EMAIL;
            } else {
                user = userService.getLoginUser(account, "", "");
                loginType = LoginType.LOGIN_USERNAME;
            }
        } else {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!BCrypt.checkpw(reqVO.getPassword(), user.getPassword())) {
            createLoginLog(user.getId(), account, loginType, LoginResult.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), Boolean.TRUE)) {
            createLoginLog(user.getId(), account, loginType, LoginResult.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }

        // 创建 Token 令牌，记录登录日志
        LoginUtil.login(user, DeviceType.PC);
        createLoginLog(user.getId(), account, loginType, LoginResult.SUCCESS);
        return new LoginRespVO()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getNickname())
                .setDesc(user.getRemark())
                .setAccessToken(StpUtil.getTokenValue());
    }

    public LoginRespVO login(EmailLoginVO reqVO) {
        String email = reqVO.getEmail();
        String code = reqVO.getCode();
        LoginUser user;
        LoginType loginType;
        if (StrUtil.isNotEmpty(email) && ReUtil.isMatch(RegexUtil.EMAIL, email)) {
            user = userService.getLoginUser("", "", email);
            loginType = LoginType.LOGIN_EMAIL_CODE;
        } else {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        String emailCode = valueOperations.get(RedisKey.EMAIL_CODE);
        if (!code.equals(emailCode)) {
            createLoginLog(user.getId(), email, loginType, LoginResult.BAD_EMAIL_CODE);
            throw exception(AUTH_LOGIN_BAD_EMAIL_CODE);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), Boolean.TRUE)) {
            createLoginLog(user.getId(), email, loginType, LoginResult.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }

        // 创建 Token 令牌，记录登录日志
        LoginUtil.login(user, DeviceType.PC);
        createLoginLog(user.getId(), email, loginType, LoginResult.SUCCESS);
        return new LoginRespVO()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getNickname())
                .setDesc(user.getRemark())
                .setAccessToken(StpUtil.getTokenValue());
    }

    public Set<String> codes() {
        Login loginUser = LoginUtil.getLoginUser();
        if (loginUser == null) {
            throw exception(UNAUTHORIZED);
        }
        return loginUser.getPermissions();
    }

    public void generateEmailCode(String account) {
    }

    public void logout() {
        Login loginUser = LoginUtil.getLoginUser();
        if (loginUser != null) {
            StpUtil.logout();
            createLoginLog(loginUser.getId(), loginUser.getUsername(), LoginType.LOGOUT_SELF, LoginResult.SUCCESS);
        }
    }

    public void register(RegisterVO reqVO) {
        String username = reqVO.getUsername();
        String password = reqVO.getPassword();
        Integer userType = UserType.valueOf(reqVO.getUserType()).getType();
        UserCreateDTO reqDTO = new UserCreateDTO();
        reqDTO.setUsername(username);
        reqDTO.setNickname(username);
        reqDTO.setPassword(BCrypt.hashpw(password));
        reqDTO.setType(userType);
        userService.registerUser(reqDTO);
    }

    private void createLoginLog(Long userId, String account, LoginType loginType, LoginResult loginResult) {
        // 插入登录日志
        LoginLogCreateDTO reqDTO = new LoginLogCreateDTO();
        reqDTO.setLoginType(loginType.getType());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(UserType.PC.getType());
        reqDTO.setAccount(account);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResult.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }
}
