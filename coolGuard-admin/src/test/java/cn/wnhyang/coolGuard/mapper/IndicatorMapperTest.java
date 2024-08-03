package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.AdminApplication;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.pojo.PageResult;
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

    @Test
    public void test() {
        List<Long> longs = indicatorMapper.selectIdListByScene(SceneType.APP, "phone");
        log.info("longs: {}", longs);
    }

    @Test
    public void test2() {
        IndicatorByPolicySetPageVO pageParam = new IndicatorByPolicySetPageVO();
        pageParam.setPolicySetId(1L);
        pageParam.setPageNo(1).setPageSize(10);
        PageResult<Indicator> indicatorPageResult = indicatorMapper.selectPageByScene(pageParam, SceneType.POLICY_SET, "phone_login");
        log.info("indicatorPageResult: {}", indicatorPageResult);
    }

    @Test
    public void test3() {
        List<Indicator> resultList = indicatorMapper.selectListByScene(SceneType.APP, "phone");
        log.info("resultList: {}", resultList);

    }

}
