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
public class CountIndicator extends AbstractIndicator {

    public CountIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.COUNT;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicator, RScoredSortedSet<String> set) {

        return (double) set.size();
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicator, long currentTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(currentTime, (String) eventDetail.get(FieldName.seqId));
    }

}
