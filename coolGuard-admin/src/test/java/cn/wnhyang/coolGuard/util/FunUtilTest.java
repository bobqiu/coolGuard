package cn.wnhyang.coolGuard.util;

import cn.wnhyang.coolGuard.decision.enums.LogicType;
import cn.wnhyang.coolGuard.decision.util.FunUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author wnhyang
 * @date 2024/5/16
 **/
@SpringBootTest
@Slf4j
public class FunUtilTest {

    @Test
    public void test() {
        Long l = Long.parseLong("1739631110000");
        System.out.println(l);
        log.info("{}", FunUtil.INSTANCE.today.get());
    }

    @Test
    public void test1() {
        boolean b = FunUtil.INSTANCE.stringLogicOp.apply("123", LogicType.EQ, "123");
        log.info("b: {}", Optional.of(b));
    }
}
