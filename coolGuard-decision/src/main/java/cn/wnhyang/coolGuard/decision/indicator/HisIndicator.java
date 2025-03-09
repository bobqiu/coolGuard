package cn.wnhyang.coolGuard.decision.indicator;

import cn.wnhyang.coolGuard.decision.constant.FieldCode;
import cn.wnhyang.coolGuard.decision.constant.IndicatorReturnFlag;
import cn.wnhyang.coolGuard.decision.context.IndicatorContext;
import cn.wnhyang.coolGuard.decision.enums.IndicatorType;
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

    public HisIndicator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public IndicatorType getType() {
        return IndicatorType.HIS;
    }

    @Override
    public Object getResult0(IndicatorContext.IndicatorCtx indicatorCtx, RScoredSortedSet<String> set) {
        // TODO 问题这样取值，类型都是字符串
        if (!set.isEmpty()) {
            if (IndicatorReturnFlag.EARLIEST.equals(indicatorCtx.getReturnFlag())) {
                return set.first().split("-")[1];
            } else if (IndicatorReturnFlag.LATEST.equals(indicatorCtx.getReturnFlag())) {
                return set.last().split("-")[1];
            }
        }
        return "";
    }

    @Override
    public void addEvent(IndicatorContext.IndicatorCtx indicatorCtx, long eventTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set) {
        set.add(eventTime, eventDetail.get(FieldCode.SEQ_ID) + "-" + eventDetail.get(indicatorCtx.getCalcField()));

    }
}
