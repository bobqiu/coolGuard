package cn.wnhyang.coolguard.mapper;

import cn.wnhyang.coolguard.AdminApplication;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.constant.SceneType;
import cn.wnhyang.coolguard.decision.entity.Indicator;
import cn.wnhyang.coolguard.decision.mapper.IndicatorMapper;
import cn.wnhyang.coolguard.decision.vo.page.IndicatorByPolicySetPageVO;
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

}
