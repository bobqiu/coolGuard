package cn.wnhyang.coolGuard.indicator;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.WinType;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.enums.IndicatorType;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
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
    protected Indicator indicator;

    protected String redisKey;

    /**
     * 指标类型
     */
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
     * 获取指标状态
     *
     * @return true/false
     */
    public boolean getStatus() {
        return indicator.getStatus();
    }

    /**
     * 过滤
     *
     * @param bindCmp 必要组件入参
     * @return true/false
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = "indicatorConditionIF", nodeType = NodeTypeEnum.BOOLEAN)
    public boolean filter(NodeComponent bindCmp) {

        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);
        // 1、主属性、从属性不为空
        if (indicator.getMasterField() != null && decisionRequest.getStringData(indicator.getMasterField()) != null) {
            if (indicator.getSlaveFields() != null) {
                String[] split = indicator.getSlaveFields().split(",");
                for (String s : split) {
                    if (decisionRequest.getStringData(s) == null) {
                        return false;
                    }
                }
                // 2、过滤脚本
                if (indicator.getComputeScript() == null) {
                    return true;
                } else {
                    // TODO 脚本过滤
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 设置redis key
     *
     * @param bindCmp 必要组件入参
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "setIndicatorRedisKey", nodeType = NodeTypeEnum.COMMON)
    public void setRedisKey(NodeComponent bindCmp) {
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        this.redisKey = RedisKey.INDICATOR + indicator.getId() + ":"
                + INDICATOR_TYPE.getType() + ":" + decisionRequest.getStringData(indicator.getMasterField())
                + "-" + decisionRequest.getStringData(indicator.getSlaveFields());

    }

    /**
     * 获取计算指标结果
     *
     * @param currentTime 当前时间戳
     * @param set         redis set
     * @return 计算指标结果
     */
    public abstract double getResult(long currentTime, RScoredSortedSet<String> set);

    /**
     * 获取计算指标结果
     *
     * @return 计算指标结果
     */
    public double getResult() {
        // 1、获取当前时间戳
        long currentTime = System.currentTimeMillis();
        // 2、获取redis中数据
        RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(redisKey);
        // 3、清理过期数据
        cleanExpiredDate(currentTime, set);

        return getResult(currentTime, set);
    }

    /**
     * 计算指标
     *
     * @param indicator   指标
     * @param eventDetail 事件详情
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "indicatorCompute", nodeType = NodeTypeEnum.COMMON)
    public void compute(NodeComponent bindCmp, Indicator indicator, Map<String, String> eventDetail) {

        if (indicator == null) {
            return;
        } else {
            this.indicator = indicator;
        }
        // 1、状态检查和过滤
        if (getStatus() && filter(bindCmp)) {


            // 2、获取当前时间戳
            long currentTime = System.currentTimeMillis();
            // 3、获取redis中数据
            log.info("redisKey:{}", redisKey);
            RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(redisKey);
            if (WinType.LAST.equals(this.indicator.getWinType())) {
                set.expire(Duration.ofSeconds(this.indicator.getTimeSlice() * this.indicator.getWinCount()));
            } else if (WinType.CUR.equals(this.indicator.getWinType())) {
                set.expire(Duration.ofSeconds(this.indicator.getTimeSlice()));
            }

            // 4、添加事件
            addEvent(currentTime, set, eventDetail);

            // 5、清理过期数据
            cleanExpiredDate(currentTime, set);
        }

    }

    /**
     * 添加事件
     *
     * @param currentTime 当前时间戳
     * @param set         redis set
     * @param eventDetail 事件详情
     */
    public abstract void addEvent(long currentTime, RScoredSortedSet<String> set, Map<String, String> eventDetail);

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
    public long calculateEpochMilli(LocalDateTime now) {
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
