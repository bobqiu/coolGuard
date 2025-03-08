package cn.wnhyang.coolGuard.filter;

import cn.wnhyang.coolGuard.constant.TraceConstants;
import cn.wnhyang.coolGuard.context.TraceContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * @author wnhyang
 * @date 2025/3/3
 **/
@Slf4j
public class TraceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // 生成并设置TraceID
            String traceId = TraceContext.generateTraceId();
            TraceContext.setTraceId(traceId);
            MDC.put(TraceConstants.TRACE_KEY, traceId);

            // 透传TraceID到下游（可选）
            if (response instanceof HttpServletResponse httpResponse) {
                httpResponse.setHeader("X-Trace-Id", traceId);
            }

            chain.doFilter(request, response);
        } finally {
            // 必须清理上下文
            TraceContext.clear();
            MDC.clear();
        }
    }
}
