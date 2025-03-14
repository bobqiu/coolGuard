package cn.wnhyang.coolguard.satoken.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.wnhyang.coolguard.satoken.service.StpInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wnhyang
 * @date 2024/3/23
 **/
@Slf4j
@Configuration
public class SaTokenConfig {

    @Bean
    public StpInterface stpInterface() {
        log.info("Sa-Token 配置成功");
        return new StpInterfaceImpl();
    }
}
