package cn.wnhyang.coolGuard.mapper;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.dto.RuleDTO;
import cn.wnhyang.coolGuard.decision.mapper.RuleMapper;
import cn.wnhyang.coolGuard.decision.vo.page.RulePageVO;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author wnhyang
 * @date 2024/7/21
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class RuleMapperTest {

    @Resource
    private RuleMapper ruleMapper;

    @Test
    public void test() {
        ruleMapper.selectRunningListByPolicyCode("phone_login_worst");
    }

    @Test
    public void test3() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        String fun = "base + al * value";
        String express = StrUtil.format("min(upperLimit, max({}, lowerLimit))", fun);
        log.info(express);
        String[] outVarNames = runner.getOutVarNames(express);
        log.info(Arrays.toString(outVarNames));
        context.put("base", 45.434);
        context.put("al", 3.352);
        context.put("value", 24.3264);
        context.put("lowerLimit", -35.342);
        context.put("upperLimit", 3463.57);
        Object r = runner.execute(express, context, null, true, false);
        log.info("{}", r);
    }

    @Test
    public void test4() {
        RulePageVO pageVO = new RulePageVO();
        pageVO.setPageNo(1).setPageSize(10);
//        pageVO.setLatest(true);
        pageVO.setHasVersion(true);
        PageResult<RuleDTO> ruleDTOPageResult = ruleMapper.selectPage(pageVO);
    }
}
