package cn.wnhyang.coolGuard.thread;

import cn.wnhyang.coolGuard.common.constant.TraceConstants;
import cn.wnhyang.coolGuard.common.context.TraceContext;
import com.alibaba.ttl.TtlRunnable;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author wnhyang
 * @date 2025/3/3
 **/
public class ThreadPoolFactory {
    public static ThreadPoolTaskExecutor createExecutor(int coreSize,
                                                        int maxSize,
                                                        int queueCapacity,
                                                        String threadNamePrefix,
                                                        RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.setTaskDecorator(getTraceContextDecorator());
        executor.initialize();
        return executor;
    }

    private static TaskDecorator getTraceContextDecorator() {
        return runnable -> TtlRunnable.get(() -> {
            try {
                MDC.put(TraceConstants.TRACE_KEY, TraceContext.getTraceId());
                runnable.run();
            } finally {
                MDC.clear();
            }
        });
    }
}
