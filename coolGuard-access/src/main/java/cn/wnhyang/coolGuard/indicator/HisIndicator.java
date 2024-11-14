package cn.wnhyang.coolGuard.indicator;

import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.constant.IndicatorReturnFlag;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/11/13
 **/
@Component
public class HisIndicator extends AbstractIndicator {

    protected HisIndicator(RedissonClient redissonClient) {
        super(IndicatorType.HIS, redissonClient);
    }

    @Override
    public Object getResult(long currentTime, RScoredSortedSet<String> set) {
        if (!set.isEmpty()) {
            if (IndicatorReturnFlag.EARLIEST.equals(indicator.getReturnFlag())) {
                return set.first().split("-")[1];
            } else if (IndicatorReturnFlag.LATEST.equals(indicator.getReturnFlag())) {
                return set.last().split("-")[1];
            }
        }
        return "";
    }

    @Override
    public void addEvent(long currentTime, RScoredSortedSet<String> set, Map<String, Object> eventDetail) {
        set.add(currentTime, eventDetail.get(FieldName.seqId) + "-" + eventDetail.get(indicator.getCalcField()));

    }
}
