package cn.wnhyang.coolGuard.indicator;

import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/12
 **/
@Component
public class AssIndicator extends AbstractIndicator {

    public AssIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.ASS;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicator, RScoredSortedSet<String> set) {

        return set.size();
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicator, long eventTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(eventTime, eventDetail.get(indicator.getCalcField()).toString());
    }
}
