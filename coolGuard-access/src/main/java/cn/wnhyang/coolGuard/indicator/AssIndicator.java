package cn.wnhyang.coolGuard.indicator;

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

    protected AssIndicator(RedissonClient redissonClient) {
        super(IndicatorType.ASS, redissonClient);
    }

    @Override
    public Object getResult(long currentTime, RScoredSortedSet<String> set) {

        return set.size();
    }

    @Override
    public void addEvent(long currentTime, RScoredSortedSet<String> set, Map<String, Object> eventDetail) {

        set.add(currentTime, (String) eventDetail.get(indicator.getCalcField()));

    }
}
