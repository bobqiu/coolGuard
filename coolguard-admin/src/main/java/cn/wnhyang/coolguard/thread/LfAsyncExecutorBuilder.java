package cn.wnhyang.coolguard.thread;

import com.yomahub.liteflow.thread.ExecutorBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wnhyang
 * @date 2025/3/3
 **/
public class LfAsyncExecutorBuilder implements ExecutorBuilder {

    @Override
    public ExecutorService buildExecutor() {
        return ThreadPoolFactory.createExecutor(
                16,
                16,
                512,
                "lfAsync-",
                new ThreadPoolExecutor.CallerRunsPolicy()
        ).getThreadPoolExecutor();
    }
}
