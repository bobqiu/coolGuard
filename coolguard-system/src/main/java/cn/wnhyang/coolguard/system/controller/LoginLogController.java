package cn.wnhyang.coolguard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.enums.OperateType;
import cn.wnhyang.coolguard.system.convert.LoginLogConvert;
import cn.wnhyang.coolguard.system.entity.LoginLogDO;
import cn.wnhyang.coolguard.system.service.LoginLogService;
import cn.wnhyang.coolguard.system.vo.loginlog.LoginLogPageVO;
import cn.wnhyang.coolguard.system.vo.loginlog.LoginLogVO;
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
    @OperateLog(module = "后台-登录日志", name = "分页查询登录日志", type = OperateType.GET)
    public CommonResult<PageResult<LoginLogVO>> getLoginLogPage(@Valid LoginLogPageVO reqVO) {
        PageResult<LoginLogDO> page = loginLogService.getLoginLogPage(reqVO);
        return CommonResult.success(LoginLogConvert.INSTANCE.convertPage(page));
    }
}
