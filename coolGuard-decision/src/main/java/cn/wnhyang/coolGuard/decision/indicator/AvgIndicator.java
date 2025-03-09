package cn.wnhyang.coolGuard.decision.indicator;

import cn.wnhyang.coolGuard.decision.constant.FieldCode;
import cn.wnhyang.coolGuard.decision.context.IndicatorContext;
import cn.wnhyang.coolGuard.decision.enums.IndicatorType;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/11
 **/
@Component
public class AvgIndicator extends AbstractIndicator {

    public AvgIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.AVG;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicatorCtx, RScoredSortedSet<String> set) {
        return set.stream()
                .mapToDouble(s -> Double.parseDouble(s.split("-")[1]))
                .average();
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicatorCtx, long eventTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(eventTime, eventDetail.get(FieldCode.SEQ_ID) + "-" + eventDetail.get(indicatorCtx.getCalcField()));

    }

}
