package cn.wnhyang.coolGuard.web.filter;

import cn.wnhyang.coolGuard.common.constant.TraceConstants;
import cn.wnhyang.coolGuard.common.context.TraceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author wnhyang
 * @date 2025/3/3
 **/
@Slf4j
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 生成并设置TraceID
            String traceId = TraceContext.generateTraceId();
            TraceContext.setTraceId(traceId);
            MDC.put(TraceConstants.TRACE_KEY, traceId);

            // 透传TraceID到下游（可选）
            response.setHeader(TraceConstants.TRACE_HEADER_KEY, traceId);

            filterChain.doFilter(request, response);
        } finally {
            // 必须清理上下文
            TraceContext.clear();
            MDC.clear();
        }
    }

}
