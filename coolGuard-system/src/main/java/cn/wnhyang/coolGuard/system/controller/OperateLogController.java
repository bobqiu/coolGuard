package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import cn.wnhyang.coolGuard.system.convert.OperateLogConvert;
import cn.wnhyang.coolGuard.system.service.OperateLogService;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志
 *
 * @author wnhyang
 * @since 2023/06/05
 */
@RestController
@RequestMapping("/system/operateLog")
@RequiredArgsConstructor
public class OperateLogController {

    private final OperateLogService operateLogService;

    /**
     * 分页查询操作日志
     *
     * @param reqVO 请求参数
     * @return 操作日志分页结果
     */
    @GetMapping("/page")
    @SaCheckPermission("system:operateLog:query")
    @OperateLog(module = "后台-操作日志", name = "分页查询操作日志", type = OperateType.GET)
    public CommonResult<PageResult<OperateLogVO>> getOperateLogPage(@Valid OperateLogPageVO reqVO) {
        return CommonResult.success(OperateLogConvert.INSTANCE.convert(operateLogService.getOperateLogPage(reqVO)));
    }
}
