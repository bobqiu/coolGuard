package cn.wnhyang.coolguard.mapper;

import cn.wnhyang.coolguard.AdminApplication;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.dto.PolicyDTO;
import cn.wnhyang.coolguard.decision.mapper.PolicyMapper;
import cn.wnhyang.coolguard.decision.vo.page.PolicyPageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wnhyang
 * @date 2024/7/21
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class PolicyMapperTest {

    @Resource
    private PolicyMapper policyMapper;

    @Test
    public void test() {
        PolicyPageVO pageVO = new PolicyPageVO();
        pageVO.setPageNo(1).setPageSize(10);
//        pageVO.setLatest(true);
        pageVO.setHasVersion(true);
        PageResult<PolicyDTO> policyDTOPageResult = policyMapper.selectPage(pageVO);
    }

}
