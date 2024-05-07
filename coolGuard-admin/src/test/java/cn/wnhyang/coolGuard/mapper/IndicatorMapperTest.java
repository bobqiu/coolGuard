package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/5/6
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class IndicatorMapperTest {

    @Resource
    private IndicatorMapper indicatorMapper;

    @Test
    public void test() {
        List<Long> longs = indicatorMapper.selectIdListByScene("test", "test");
        log.info("longs: {}", longs);
    }
}
