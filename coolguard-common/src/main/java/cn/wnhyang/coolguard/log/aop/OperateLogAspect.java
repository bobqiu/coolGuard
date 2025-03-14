package cn.wnhyang.coolguard.log.aop;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.wnhyang.coolguard.common.context.TraceContext;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.util.JsonUtil;
import cn.wnhyang.coolguard.common.util.ServletUtils;
import cn.wnhyang.coolguard.log.annotation.OperateLog;
import cn.wnhyang.coolguard.log.dto.LogCreateReqDTO;
import cn.wnhyang.coolguard.log.enums.OperateType;
import cn.wnhyang.coolguard.log.service.LogService;
import cn.wnhyang.coolguard.satoken.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import static cn.wnhyang.coolguard.common.exception.GlobalErrorCode.INTERNAL_SERVER_ERROR;
import static cn.wnhyang.coolguard.common.exception.GlobalErrorCode.SUCCESS;


/**
 * 操作日志注解切面
 *
 * @author wnhyang
 */
@Aspect
@Slf4j
@Setter
public class OperateLogAspect {

    private LogService logService;

    @Around("@annotation(operateLog) && @within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint joinPoint, OperateLog operateLog) throws Throwable {
        return around0(joinPoint, operateLog);
    }

    @Around("!@annotation(cn.wnhyang.coolguard.log.annotation.OperateLog) && @within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return around0(joinPoint, null);
    }

    public Object around0(ProceedingJoinPoint joinPoint, OperateLog operateLog) throws Throwable {

        // 记录开始时间
        LocalDateTime startTime = LocalDateTime.now();
        Object result = null;
        LogCreateReqDTO operateLogObj = null;
        try {

            operateLogObj = new LogCreateReqDTO();

            operateLogObj.setTraceId(TraceContext.getTraceId());
            // 补全通用字段
            operateLogObj.setStartTime(startTime);
            // 补充用户信息
            try {
                operateLogObj.setUserId(LoginUtil.getUserId());
                operateLogObj.setUserNickname(LoginUtil.getLoginUser().getNickname());
            } catch (Exception e) {
                operateLogObj.setUserId(0L);
            }

            // 补全请求信息
            // 获得 Request 对象
            HttpServletRequest request = ServletUtils.getRequest();
            if (request != null) {
                operateLogObj.setRequestMethod(request.getMethod());
                operateLogObj.setRequestUrl(request.getRequestURI());
                operateLogObj.setRequestParams(JsonUtil.toJsonString(joinPoint.getArgs()));
                operateLogObj.setUserIp(ServletUtils.getClientIP(request));
                operateLogObj.setUserAgent(ServletUtils.getUserAgent(request));
            }

            // 补全模块信息
            String operateModule = operateLog != null ? operateLog.module() : null;
            String operateName = operateLog != null ? operateLog.name() : null;
            OperateType operateType = operateLog != null && operateLog.type().length > 0 ? operateLog.type()[0] : parseOperateLogType(request);
            operateLogObj.setModule(operateModule).setName(operateName).setType(operateType.getType());

            // 执行原有方法
            result = joinPoint.proceed();

            // 补全结果信息
            fillResultFields(operateLogObj, startTime, result, null);

            log.info(operateLogObj.toString());

            // 判断不记录的情况
            if (!isLogEnable(operateLog)) {
                return result;
            }
            // 目前，只有管理员，才记录操作日志！所以非管理员，直接调用，不进行记录

            // 异步记录日志
            logService.createLog(operateLogObj);

            return result;
        } catch (Throwable exception) {
            if (operateLogObj != null) {
                log.error(operateLogObj.toString());
                // 补全结果信息
                fillResultFields(operateLogObj, startTime, result, exception);
            }
            throw exception;
        }
    }

    private static void fillResultFields(LogCreateReqDTO operateLogObj,
                                         LocalDateTime startTime, Object result, Throwable exception) {
        LocalDateTime endTime = LocalDateTime.now();
        operateLogObj.setEndTime(endTime);
        operateLogObj.setDuration((int) (LocalDateTimeUtil.between(startTime, endTime).toMillis()));

        // （正常）处理 resultCode 和 resultMsg 字段
        if (result instanceof CommonResult<?> commonResult) {
            operateLogObj.setResultCode(commonResult.getCode());
            operateLogObj.setResultMsg(commonResult.getMsg());
            operateLogObj.setResultData(JsonUtil.toJsonString(commonResult.getData()));
        } else {
            operateLogObj.setResultCode(SUCCESS.getCode());
        }
        // （异常）处理 resultCode 和 resultMsg 字段
        if (exception != null) {
            operateLogObj.setResultCode(INTERNAL_SERVER_ERROR.getCode());
            operateLogObj.setResultMsg(ExceptionUtil.getRootCauseMessage(exception));
        }
    }

    private static OperateType parseOperateLogType(HttpServletRequest request) {
        RequestMethod requestMethod = RequestMethod.resolve(request.getMethod());
        if (requestMethod == null) {
            return OperateType.OTHER;
        }
        return switch (requestMethod) {
            case GET -> OperateType.GET;
            case POST -> OperateType.CREATE;
            case PUT -> OperateType.UPDATE;
            case DELETE -> OperateType.DELETE;
            default -> OperateType.OTHER;
        };
    }

    private static boolean isLogEnable(OperateLog operateLog) {
        return operateLog != null && operateLog.enable();
    }

    private Object[] filterSensitiveParams(Object[] args) {
        return Arrays.stream(args)
                .peek(arg -> {
                    if (arg instanceof Map) {
                        ((Map<?, ?>) arg).entrySet().removeIf(entry ->
                                "password".equals(entry.getKey()) ||
                                        "token".equals(entry.getKey()));
                    }
                })
                .toArray();
    }

}
