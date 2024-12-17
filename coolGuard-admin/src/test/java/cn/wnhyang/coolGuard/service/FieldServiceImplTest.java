package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.vo.create.TestDynamicFieldScript;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/12/17
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class FieldServiceImplTest {

    @Resource(name = "fieldServiceImpl")
    private FieldService fieldService;

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("N_D_transTime", "2024-12-17 12:29:35");
        map.put("N_S_transSerialNo", "2024-12-17 12:29:35");
        String result = fieldService.testDynamicFieldScript(new TestDynamicFieldScript(map, "N_S_transSerialNo.substring(11,13)"));
        log.info("result: {}", result);
    }
}
