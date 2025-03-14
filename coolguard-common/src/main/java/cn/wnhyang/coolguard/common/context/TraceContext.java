package cn.wnhyang.coolguard.common.context;

import cn.hutool.core.util.IdUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 基于TransmittableThreadLocal实现线程池安全的TraceID传递
 *
 * @author wnhyang
 * @date 2025/3/3
 **/
public class TraceContext {

    private static final TransmittableThreadLocal<String> TRACE_ID = new TransmittableThreadLocal<>();

    /**
     * 设置TraceID，并同步到Log4j2的MDC
     */
    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static void clear() {
        TRACE_ID.remove();
    }

    public static String generateTraceId() {
        return IdUtil.simpleUUID();
    }
}
