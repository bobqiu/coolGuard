package cn.wnhyang.coolGuard.mapper;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Cond;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.util.LFUtil;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/7/21
 **/
@SpringBootTest(classes = AdminApplication.class)
@Slf4j
public class RuleMapperTest {

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private ChainMapper chainMapper;

    @Test
    public void test() {
        ruleMapper.selectRunningListByPolicyCode("phone_login_worst");
    }

    private Cond getCond(String code) {
        Chain chain = chainMapper.getByChainName(StrUtil.format(LFUtil.RULE_CHAIN, code));
        List<String> ifEl = LFUtil.parseIfEl(chain.getElData());
        return LFUtil.parseToCond(ifEl.get(0));
    }

    @Test
    public void test2() {
        List<Rule> ruleList = ruleMapper.selectList();
        for (Rule rule : ruleList) {
            rule.setCond(getCond(rule.getCode()));
            ruleMapper.updateById(rule);
        }
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
}
