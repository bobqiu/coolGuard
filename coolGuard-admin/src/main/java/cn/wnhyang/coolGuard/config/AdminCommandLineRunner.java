package cn.wnhyang.coolGuard.config;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wnhyang
 * @date 2024/12/1
 **/
@Component
@RequiredArgsConstructor
public class AdminCommandLineRunner implements CommandLineRunner {

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        JacksonTypeHandler.setObjectMapper(objectMapper);
    }
}
