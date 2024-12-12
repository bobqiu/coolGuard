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
 * @date 2024/3/11
 **/
@Component
public class SumIndicator extends AbstractIndicator {

    public SumIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.SUM;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicator, RScoredSortedSet<String> set) {

        double sum = 0.0;
        for (String item : set) {
            String[] split = item.split("-");
            if (split.length >= 2) {
                sum = sum + Double.parseDouble(split[1]);
            }
        }

        return sum;
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicator, long currentTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(currentTime, eventDetail.get(FieldName.seqId) + "-" + eventDetail.get(indicator.getCalcField()));

    }

}
