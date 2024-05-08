package cn.wnhyang.coolGuard.indicator;

import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.enums.IndicatorType;
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

    protected AvgIndicator(RedissonClient redissonClient) {
        super(IndicatorType.AVG, redissonClient);
    }

    @Override
    public double getResult(long currentTime, RScoredSortedSet<String> set) {
        double sum = 0.0;
        for (String item : set) {
            String[] split = item.split("-");
            if (split.length >= 2) {
                sum = sum + Double.parseDouble(split[1]);
            }
        }
        // sum除set的长度,四舍五入

        return sum / set.size();
    }

    @Override
    public void addEvent(long currentTime, RScoredSortedSet<String> set, Map<String, Object> eventDetail) {

        set.add(currentTime, eventDetail.get(FieldName.seqId) + "-" + eventDetail.get(indicator.getCalcField()));

    }

}
