package cn.wnhyang.coolGuard.aspect;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Aspect
@Slf4j
@Component
public class TraceAspect {

    private static final String TRACE_ID = "traceId";

    /**
     * 对所有controller切面
     *
     * @param pjp ProceedingJoinPoint
     * @return 结果
     * @throws Throwable 异常
     */
    @Around("execution(* cn.wnhyang.coolGuard.controller.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            String traceId = IdUtil.simpleUUID();
            MDC.put(TRACE_ID, traceId);
            return pjp.proceed();

        } finally {
            MDC.remove(TRACE_ID);
            log.info("cost:{}", System.currentTimeMillis() - startTime);
        }

    }
}
