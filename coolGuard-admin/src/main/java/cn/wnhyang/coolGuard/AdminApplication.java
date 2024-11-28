package cn.wnhyang.coolGuard;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        JacksonTypeHandler.setObjectMapper(SpringUtil.getBean(ObjectMapper.class));
    }
}
