package cn.wnhyang.coolguard.decision.indicator;

import cn.wnhyang.coolguard.decision.constant.FieldCode;
import cn.wnhyang.coolguard.decision.context.IndicatorContext;
import cn.wnhyang.coolguard.decision.enums.IndicatorType;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/11
 **/
@Component
public class CountIndicator extends AbstractIndicator {

    public CountIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.COUNT;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicatorCtx, RScoredSortedSet<String> set, long startTime, long endTime) {
        return set.count(startTime, true, endTime, true);
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicatorCtx, long eventTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(eventTime, eventDetail.get(FieldCode.SEQ_ID).toString());
    }

}
