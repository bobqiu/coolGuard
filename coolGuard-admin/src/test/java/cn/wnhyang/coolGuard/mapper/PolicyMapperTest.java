package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.dto.PolicyDTO;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
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
