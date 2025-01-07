package cn.wnhyang.coolGuard.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.OperateLogConvert;
import cn.wnhyang.coolGuard.system.entity.OperateLog;
import cn.wnhyang.coolGuard.system.entity.User;
import cn.wnhyang.coolGuard.system.service.OperateLogService;
import cn.wnhyang.coolGuard.system.service.UserService;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogVO;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    private final UserService userService;

    /**
     * 分页查询操作日志
     *
     * @param reqVO 请求参数
     * @return 操作日志分页结果
     */
    @GetMapping("/page")
    @cn.wnhyang.coolGuard.log.core.annotation.OperateLog(module = "后台-操作日志", name = "分页查询操作日志")
    @SaCheckPermission("system:operateLog:query")
    public CommonResult<PageResult<OperateLogVO>> getOperateLogPage(@Valid OperateLogPageVO reqVO) {
        PageResult<OperateLog> pageResult = operateLogService.getOperateLogPage(reqVO);

        // 获得拼接需要的数据
        Collection<Long> userIds = CollectionUtils.convertList(pageResult.getList(), OperateLog::getUserId);
        Map<Long, User> userMap = userService.getUserMap(userIds);
        // 拼接数据
        List<OperateLogVO> list = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(operateLog -> {
            OperateLogVO respVO = OperateLogConvert.INSTANCE.convert(operateLog);
            respVO.setUserNickname(userMap.get(operateLog.getUserId()).getNickname());
            list.add(respVO);
        });
        return CommonResult.success(new PageResult<>(list, pageResult.getTotal()));
    }
}
