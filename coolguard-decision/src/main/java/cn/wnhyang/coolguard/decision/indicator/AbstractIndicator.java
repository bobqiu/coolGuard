package cn.wnhyang.coolguard.decision.indicator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.decision.constant.FieldCode;
import cn.wnhyang.coolguard.decision.constant.WinType;
import cn.wnhyang.coolguard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolguard.decision.context.FieldContext;
import cn.wnhyang.coolguard.decision.context.IndicatorContext;
import cn.wnhyang.coolguard.decision.enums.IndicatorType;
import cn.wnhyang.coolguard.redis.constant.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/11
 **/
@Slf4j
public abstract class AbstractIndicator {

    /**
     * redisson客户端
     */
    protected final RedissonClient redissonClient;

    public AbstractIndicator(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String getTypeCode() {
        return getType().getType();
    }

    /**
     * 获取指标类型
     *
     * @return 指标类型
     */
    public abstract IndicatorType getType();

    /**
     * 过滤
     *
     * @return true/false
     */
    private boolean filter(IndicatorContext.IndicatorCtx indicatorCtx, Map<String, Object> eventDetail) {
        // 1、主属性不能为空，并且主属性取值也不能为空
        if (StrUtil.isNotBlank(indicatorCtx.getMasterField()) && ObjectUtil.isNotNull(eventDetail.get(indicatorCtx.getMasterField()))) {
            if (CollUtil.isNotEmpty(indicatorCtx.getSlaveFields())) {
                for (String s : indicatorCtx.getSlaveFields()) {
                    if (eventDetail.get(s) == null) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置redis key
     * 如：masterField分别为a、b，其在事件中的取值可能一样，那么指标key也就一样，可以优化为a：xxx，b：xxx
     */
    private String getRedisKey(IndicatorContext.IndicatorCtx indicatorCtx, Map<String, Object> eventDetail) {
        StringBuilder redisKey = new StringBuilder(RedisKey.ZB + indicatorCtx.getCode() + ":" + eventDetail.get(indicatorCtx.getMasterField()));
        if (CollUtil.isNotEmpty(indicatorCtx.getSlaveFields())) {
            for (String slaveField : indicatorCtx.getSlaveFields()) {
                redisKey.append("-").append(eventDetail.get(slaveField));
            }
        }
        return redisKey.toString();
    }

    /**
     * 获取计算指标结果
     *
     * @param set redis set
     * @return 计算指标结果
     */
    public abstract Object getResult0(IndicatorContext.IndicatorCtx indicatorCtx, RScoredSortedSet<String> set, long startTime, long endTime);

    /**
     * 获取计算指标结果
     *
     * @return 计算指标结果
     */
    public Object getResult(long eventTime, IndicatorContext.IndicatorCtx indicatorCtx, RScoredSortedSet<String> set) {
        // 1、清理过期数据
        cleanExpiredDate(indicatorCtx, eventTime, set);
        long startTime = eventTime;
        // 2、设置过期时间
        if (WinType.LAST.equals(indicatorCtx.getWinType())) {
            startTime = eventTime - indicatorCtx.getTimeSlice() * indicatorCtx.getWinCount() * 1000;
            set.expire(Duration.ofSeconds(indicatorCtx.getTimeSlice() * indicatorCtx.getWinCount()));
        } else if (WinType.CUR.equals(indicatorCtx.getWinType())) {
            startTime = eventTime - indicatorCtx.getTimeSlice() * 1000;
            set.expire(Duration.ofSeconds(indicatorCtx.getTimeSlice()));
        }
        return getResult0(indicatorCtx, set, startTime, eventTime);
    }

    /**
     * 计算指标
     *
     * @param addEvent 是否添加事件
     */
    public boolean compute(boolean addEvent, IndicatorContext.IndicatorCtx indicatorCtx) {
        if (indicatorCtx == null) {
            return false;
        }
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        if (!filter(indicatorCtx, fieldContext)) {
            return false;
        }
        String redisKey = getRedisKey(indicatorCtx, fieldContext);
        log.info("redisKey:{}", redisKey);
        RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(redisKey);
        long eventTime = Long.parseLong(fieldContext.getData2String(FieldCode.EVENT_TIME_STAMP));
        // 1、状态检查和过滤
        if (addEvent) {
            // 2、添加事件
            addEvent(indicatorCtx, eventTime, fieldContext, set);

        }
        Object result = getResult(eventTime, indicatorCtx, set);
        indicatorCtx.setValue(result);
        return true;
    }

    /**
     * 添加事件
     *
     * @param eventTime   当前时间戳
     * @param eventDetail 事件详情
     */
    public abstract void addEvent(IndicatorContext.IndicatorCtx indicatorCtx, long eventTime, Map<String, Object> eventDetail, RScoredSortedSet<String> set);

    /**
     * 清理过期数据
     *
     * @param eventTime 当前时间戳
     * @param set       redis set
     */
    public void cleanExpiredDate(IndicatorContext.IndicatorCtx indicatorCtx, long eventTime, RScoredSortedSet<String> set) {
        if (WinType.LAST.equals(indicatorCtx.getWinType())) {
            set.removeRangeByScoreAsync(-1, true, eventTime - Duration.ofSeconds(indicatorCtx.getTimeSlice()).toMillis(), false);
        } else if (WinType.CUR.equals(indicatorCtx.getWinType())) {
            set.removeRangeByScoreAsync(-1, true, calculateEpochMilli(indicatorCtx, LocalDateTime.now()), false);
        }
    }

    /**
     * 计算时间戳
     *
     * @param now 当前时间
     * @return 时间戳
     */
    private long calculateEpochMilli(IndicatorContext.IndicatorCtx indicatorCtx, LocalDateTime now) {
        ZoneId zoneId = ZoneId.systemDefault();
        // 这个default分支仅处理WindowSize枚举中未包含的情况
        return switch (indicatorCtx.getWinSize()) {
            case "M" -> now.withDayOfMonth(1).with(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli();
            case "d" -> now.with(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli();
            case "H" -> now.withMinute(0).withSecond(0).withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            case "m" -> now.withSecond(0).withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            case "s" -> now.withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            default -> throw new IllegalArgumentException("Unsupported window size: " + indicatorCtx.getWinSize());
        };
    }
}
