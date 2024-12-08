package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.entity.PolicySetVersion;
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
public class PolicySetVersionMapperTest {

    @Resource
    private PolicySetMapper policySetMapper;

    @Resource
    private PolicySetVersionMapper policySetVersionMapper;

    @Test
    public void test() {
        for (PolicySet policySet : policySetMapper.selectList()) {
            log.info("{}", policySet);
            policySetVersionMapper.insert(new PolicySetVersion().setCode(policySet.getCode()).setLatest(Boolean.TRUE).setChain(policySet.getChain()));
        }
    }
}
