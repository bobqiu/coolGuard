package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.ConfigField;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wnhyang
 * @date 2024/11/28
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class AccessMapperTest {

    @Resource
    private AccessMapper accessMapper;

    @Test
    public void test() {
        Access access = accessMapper.selectByCode("publicInterface");
        log.info("access: {}", access);
        for (ConfigField configField : access.getInputConfig()) {
            log.info("configField: {}", configField);
        }

    }

    @Test
    public void test1() {
        Access access = accessMapper.selectById(1L);
        log.info("access: {}", access);
        for (ConfigField configField : access.getInputConfig()) {
            log.info("configField: {}", configField);
        }

    }


}
