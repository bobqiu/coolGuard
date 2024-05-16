package cn.wnhyang.coolGuard.util;

import cn.wnhyang.coolGuard.fun.StartTimePointFun;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author wnhyang
 * @date 2024/5/16
 **/
public class FunUtil {

    private FunUtil() {
    }

    public static final FunUtil INSTANCE = new FunUtil();

    public StartTimePointFun today = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.with(LocalTime.MIN);
    };

    public StartTimePointFun yesterday = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(1).with(LocalTime.MIN);
    };

    public StartTimePointFun last1Hours = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusHours(1);
    };

    public StartTimePointFun last24Hours = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusHours(24);
    };

    public StartTimePointFun last1Days = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(1);
    };

    public StartTimePointFun last7Days = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(7);
    };

    public StartTimePointFun last30Days = () -> {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(30);
    };

}
