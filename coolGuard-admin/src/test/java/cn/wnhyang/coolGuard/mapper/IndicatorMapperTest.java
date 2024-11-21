package cn.wnhyang.coolGuard.mapper;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.util.JsonUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import cn.wnhyang.coolGuard.vo.page.IndicatorByPolicySetPageVO;
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

    @Resource
    private ChainMapper chainMapper;

    @Test
    public void test() {
        List<Long> longs = indicatorMapper.selectIdListByScene(SceneType.APP, "phone");
        log.info("longs: {}", longs);
    }

    @Test
    public void test2() {
        IndicatorByPolicySetPageVO pageParam = new IndicatorByPolicySetPageVO();
        pageParam.setPolicySetCode("phone_login");
        pageParam.setPageNo(1).setPageSize(10);
        PageResult<Indicator> indicatorPageResult = indicatorMapper.selectPageByScene(pageParam, SceneType.POLICY_SET, "phone_login");
        log.info("indicatorPageResult: {}", indicatorPageResult);
    }

    @Test
    public void test3() {
        List<Indicator> resultList = indicatorMapper.selectListByScene(SceneType.APP, "phone");
        log.info("resultList: {}", resultList);

    }

    @Test
    public void test4() {
        List<Indicator> indicators = indicatorMapper.selectListByScenes("phone", "phone_login");
        log.info("indicators: {}", indicators);

    }

    @Test
    public void test5() {
        List<Indicator> indicatorList = indicatorMapper.selectList();
        log.info("indicatorList: {}", indicatorList);
        for (Indicator indicator : indicatorList) {
            indicator.setCondStr(JsonUtils.toJsonString(getCond(indicator.getCode())));
            indicatorMapper.updateById(indicator);
        }
    }

    private Cond getCond(String code) {
        Chain chain = chainMapper.getByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, code));
        List<String> ifEl = LFUtil.parseIfEl(chain.getElData());
        return LFUtil.parseToCond(ifEl.get(0));
    }

}
