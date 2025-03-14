package cn.wnhyang.coolguard.config;

import cn.wnhyang.coolguard.thread.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wnhyang
 * @date 2023/12/11
 **/
@EnableAsync
@Configuration
@Slf4j
public class AsyncTaskConfig {

    /**
     * 通用异步线程池
     *
     * @return 异步线程池
     */
    @Bean("asyncExecutor")
    public AsyncTaskExecutor asyncTask() {
        return ThreadPoolFactory.createExecutor(
                16,
                16,
                512,
                "asyncExecutor-",
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 指标线程池
     *
     * @return 异步线程池
     */
    @Bean("indicatorAsync")
    public AsyncTaskExecutor indicatorAsync() {
        return ThreadPoolFactory.createExecutor(
                16,
                16,
                512,
                "indicatorAsync-",
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 策略线程池
     *
     * @return 异步线程池
     */
    @Bean("policyAsync")
    public AsyncTaskExecutor policyAsync() {
        return ThreadPoolFactory.createExecutor(
                16,
                16,
                512,
                "policyAsync-",
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
