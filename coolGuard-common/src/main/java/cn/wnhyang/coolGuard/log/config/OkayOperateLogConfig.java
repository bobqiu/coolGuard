package cn.wnhyang.coolGuard.log.config;

import cn.wnhyang.coolGuard.log.aop.OperateLogAspect;
import cn.wnhyang.coolGuard.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wnhyang
 * @date 2023/5/31
 **/
@Slf4j
@Configuration
public class OkayOperateLogConfig {

    @Bean
    public OperateLogAspect operateLogAspect(LogService logService) {
        log.info("[OperateLogAspect] 创建成功");
        OperateLogAspect operateLogAspect = new OperateLogAspect();
        operateLogAspect.setLogService(logService);
        return operateLogAspect;
    }

}
