package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.vo.create.TestDynamicFieldScript;
import com.ql.util.express.DefaultContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("N_D_transTime", "2024-12-17 12:29:35");
        context.put("N_S_transSerialNo", "2024-12-17 12:29:35");
        String result = fieldService.testDynamicFieldScript(new TestDynamicFieldScript(context, "N_S_transSerialNo.substring(11,13)"));
        log.info("result: {}", result);
    }
}
