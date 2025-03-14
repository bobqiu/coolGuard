package cn.wnhyang.coolguard.satoken.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wnhyang
 * @date 2023/7/29
 **/
@Slf4j
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册sa-token的拦截器");
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin())).addPathPatterns("/**");
    }

}
