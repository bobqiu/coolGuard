package cn.wnhyang.coolGuard.indicator;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.WinType;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import lombok.Getter;
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
     * 指标
     */
    protected IndicatorContext.IndicatorCtx indicator;

    /**
     * 指标类型
     */
    @Getter
    protected final IndicatorType INDICATOR_TYPE;

    /**
     * redisson客户端
     */
    protected final RedissonClient redissonClient;

    protected AbstractIndicator(IndicatorType indicatorType, RedissonClient redissonClient) {
        this.INDICATOR_TYPE = indicatorType;
        this.redissonClient = redissonClient;
    }

    /**
     * 获取指标类型
     *
     * @return 指标类型
     */
    public String getType() {
        return INDICATOR_TYPE.getType();
    }

    /**
     * 过滤
     *
     * @return true/false
     */
    private boolean filter(Map<String, Object> eventDetail) {
        // 1、主属性不能为空，并且主属性取值也不能为空
        if (StrUtil.isNotBlank(indicator.getMasterField()) && ObjectUtil.isNotNull(eventDetail.get(indicator.getMasterField()))) {
            if (StrUtil.isNotBlank(indicator.getSlaveFields())) {
                String[] split = indicator.getSlaveFields().split(",");
                for (String s : split) {
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
    private String setRedisKey(Map<String, Object> eventDetail) {
        String redisKey = RedisKey.ZB + indicator.getCode() + ":" + eventDetail.get(indicator.getMasterField());
        if (StrUtil.isNotBlank(indicator.getSlaveFields())) {
            redisKey += "-" + eventDetail.get(indicator.getSlaveFields());
        }
        return redisKey;
    }

    /**
     * 获取计算指标结果
     *
     * @param currentTime 当前时间戳
     * @param set         redis set
     * @return 计算指标结果
     */
    public abstract Object getResult(long currentTime, RScoredSortedSet<String> set);

    /**
     * 获取计算指标结果
     *
     * @return 计算指标结果
     */
    public Object getResult(String redisKey) {
        // 1、获取当前时间戳
        long currentTime = System.currentTimeMillis();
        // 2、获取redis中数据
        RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(redisKey);
        // 3、清理过期数据
        cleanExpiredDate(currentTime, set);
        // 4、设置过期时间
        if (WinType.LAST.equals(this.indicator.getWinType())) {
            set.expire(Duration.ofSeconds(this.indicator.getTimeSlice() * this.indicator.getWinCount()));
        } else if (WinType.CUR.equals(this.indicator.getWinType())) {
            set.expire(Duration.ofSeconds(this.indicator.getTimeSlice()));
        }
        return getResult(currentTime, set);
    }

    /**
     * 计算指标
     *
     * @param indicator   指标
     * @param eventDetail 事件详情
     */
    public Object compute(boolean addEvent, IndicatorContext.IndicatorCtx indicator, Map<String, Object> eventDetail) {
        if (indicator == null) {
            return 0.0d;
        } else {
            this.indicator = indicator;
        }
        String redisKey = setRedisKey(eventDetail);
        // 1、状态检查和过滤
        if (addEvent && filter(eventDetail)) {
            // 2、获取当前时间戳
            long currentTime = System.currentTimeMillis();
            // 3、获取redis中数据
            log.info("redisKey:{}", redisKey);
            RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(redisKey);
            // 4、添加事件
            addEvent(currentTime, set, eventDetail);

        }
        return getResult(redisKey);
    }

    /**
     * 添加事件
     *
     * @param currentTime 当前时间戳
     * @param set         redis set
     * @param eventDetail 事件详情
     */
    public abstract void addEvent(long currentTime, RScoredSortedSet<String> set, Map<String, Object> eventDetail);

    /**
     * 清理过期数据
     *
     * @param currentTime 当前时间戳
     * @param set         redis set
     */
    public void cleanExpiredDate(long currentTime, RScoredSortedSet<String> set) {
        if (WinType.LAST.equals(indicator.getWinType())) {
            set.removeRangeByScore(-1, true, currentTime - Duration.ofSeconds(indicator.getTimeSlice()).toMillis(), false);
        } else if (WinType.CUR.equals(indicator.getWinType())) {
            set.removeRangeByScore(-1, true, calculateEpochMilli(LocalDateTime.now()), false);
        }
    }

    /**
     * 计算时间戳
     *
     * @param now 当前时间
     * @return 时间戳
     */
    private long calculateEpochMilli(LocalDateTime now) {
        ZoneId zoneId = ZoneId.systemDefault();
        // 这个default分支仅处理WindowSize枚举中未包含的情况
        return switch (indicator.getWinSize()) {
            case "M" -> now.withDayOfMonth(1).with(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli();
            case "d" -> now.with(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli();
            case "H" -> now.withMinute(0).withSecond(0).withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            case "m" -> now.withSecond(0).withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            case "s" -> now.withNano(0).atZone(zoneId).toInstant().toEpochMilli();
            default -> throw new IllegalArgumentException("Unsupported window size: " + indicator.getWinSize());
        };
    }
}
