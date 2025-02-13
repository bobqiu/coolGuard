package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.entity.Access;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> map = new HashMap<>();
        map.put("test", "test");
        map.put("test1", "test1");
        map.put("test2", "test2");
        Access access = new Access().setCode("12345").setName("grer").setTestParams(map);
        accessMapper.insert(access);

        Access access1 = accessMapper.selectByCode("12345");
        log.info("{}", access1);
    }

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test");
        map.put("wegw", "test1");
        map.put("test2", "test2");
        Access access = new Access().setCode("12345").setTestParams(map);
        accessMapper.updateByCode(access);

        Access access1 = accessMapper.selectByCode("12345");
        log.info("{}", access1);
    }


}
