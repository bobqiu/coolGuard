package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.system.service.AuthService;
import cn.wnhyang.coolGuard.system.vo.core.auth.EmailLoginVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.LoginReqVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.LoginRespVO;
import cn.wnhyang.coolGuard.system.vo.core.auth.RegisterVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;


/**
 * 授权服务
 *
 * @author wnhyang
 * @date 2023/7/26
 **/
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
@SaIgnore
public class AuthController {

    private final AuthService authService;

    /**
     * 登录
     *
     * @param reqVO 登录请求
     * @return token
     */
    @PostMapping("/login")
    public CommonResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    /**
     * 获取权限码
     *
     * @return 权限码集合
     */
    @GetMapping("/codes")
    public CommonResult<Set<String>> codes() {
        return success(authService.codes());
    }

    /**
     * 通过邮箱+验证码登录
     *
     * @param reqVO 邮箱+验证码
     * @return token
     */
    @PostMapping("/loginByEmail")
    public CommonResult<LoginRespVO> login(@RequestBody @Valid EmailLoginVO reqVO) {
        return success(authService.login(reqVO));
    }

    /**
     * 获取验证码
     *
     * @return true
     */
    @PostMapping("/generate/emailCode")
    public CommonResult<Boolean> generateCode(@RequestParam("account") String account) {
        authService.generateEmailCode(account);
        return success(true);
    }

    @DeleteMapping("/logout")
    public CommonResult<Boolean> logout() {
        authService.logout();
        return success(true);
    }

    /**
     * 注册
     *
     * @param reqVO 注册信息
     * @return 加密数据
     */
    @GetMapping("/register")
    public CommonResult<Boolean> register(@RequestParam RegisterVO reqVO) {
        authService.register(reqVO);
        return success(true);
    }
}
