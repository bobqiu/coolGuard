package cn.wnhyang.coolGuard.mapper;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
}
