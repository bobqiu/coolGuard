package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.decision.entity.Rule;
import cn.wnhyang.coolGuard.decision.mapper.RuleMapper;
import cn.wnhyang.coolGuard.decision.mapper.RuleVersionMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wnhyang
 * @date 2024/12/1
 **/
@Slf4j
@SpringBootTest(classes = AdminApplication.class)
public class RuleVersionMapperTest {

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private RuleVersionMapper ruleVersionMapper;

    @Test
    void test() {
        for (Rule rule : ruleMapper.selectList()) {
            log.info("{}", rule);
        }
    }
}
