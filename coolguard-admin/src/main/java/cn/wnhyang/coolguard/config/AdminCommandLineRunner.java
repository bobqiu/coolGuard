package cn.wnhyang.coolguard.config;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wnhyang
 * @date 2024/12/1
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminCommandLineRunner implements CommandLineRunner {

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        log.info("JacksonTypeHandler.setObjectMapper");
        JacksonTypeHandler.setObjectMapper(objectMapper);
    }
}
