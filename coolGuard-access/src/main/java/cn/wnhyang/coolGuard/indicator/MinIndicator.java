package cn.wnhyang.coolGuard.indicator;

import cn.wnhyang.coolGuard.constant.FieldName;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/5/10
 **/
@Component
public class MinIndicator extends AbstractIndicator {

    protected MinIndicator(RedissonClient redissonClient) {
        super(IndicatorType.MIN, redissonClient);
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicator, RScoredSortedSet<String> set) {
        double min = Double.MAX_VALUE;
        for (String item : set) {
            String[] split = item.split("-");
            if (split.length >= 2) {
                double v = Double.parseDouble(split[1]);
                if (v < min) {
                    min = v;
                }
            }
        }

        return min;
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicator, long currentTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(currentTime, eventDetail.get(FieldName.seqId) + "-" + eventDetail.get(indicator.getCalcField()));

    }
}
