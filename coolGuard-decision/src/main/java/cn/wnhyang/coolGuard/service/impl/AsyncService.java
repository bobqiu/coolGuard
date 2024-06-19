package cn.wnhyang.coolGuard.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author wnhyang
 * @date 2024/6/19
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncService {

    private final AsyncTaskExecutor asyncExecutor;

    public void asyncTask(Runnable runnable) {
        asyncExecutor.execute(runnable);
    }

}
