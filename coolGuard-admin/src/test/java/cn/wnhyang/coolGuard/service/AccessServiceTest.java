package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.decision.service.AccessService;
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
public class AccessServiceTest {

    @Resource
    private AccessService accessService;

    @Test
    public void test() {

    }
}
