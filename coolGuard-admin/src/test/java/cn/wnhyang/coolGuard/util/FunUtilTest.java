package cn.wnhyang.coolGuard.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wnhyang
 * @date 2024/5/16
 **/
@SpringBootTest
@Slf4j
public class FunUtilTest {

    @Test
    public void test() {
        log.info("{}", FunUtil.INSTANCE.today.get());
    }
}
