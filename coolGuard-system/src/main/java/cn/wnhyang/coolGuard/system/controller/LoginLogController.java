package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.LoginLogConvert;
import cn.wnhyang.coolGuard.system.entity.LoginLogDO;
import cn.wnhyang.coolGuard.system.service.LoginLogService;
import cn.wnhyang.coolGuard.system.vo.loginlog.LoginLogPageVO;
import cn.wnhyang.coolGuard.system.vo.loginlog.LoginLogVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 *
 * @author wnhyang
 * @since 2023/07/25
 */
@RestController
@RequestMapping("/system/loginLog")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    /**
     * 分页查询登录日志
     *
     * @param reqVO 分页请求
     * @return 分页登录日志
     */
    @GetMapping("/page")
    @SaCheckPermission("system:loginLog:query")
    public CommonResult<PageResult<LoginLogVO>> getLoginLogPage(@Valid LoginLogPageVO reqVO) {
        PageResult<LoginLogDO> page = loginLogService.getLoginLogPage(reqVO);
        return CommonResult.success(LoginLogConvert.INSTANCE.convertPage(page));
    }
}
