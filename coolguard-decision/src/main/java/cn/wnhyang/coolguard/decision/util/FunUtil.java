package cn.wnhyang.coolguard.decision.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReUtil;
import cn.wnhyang.coolguard.common.fun.StartTimePointFun;
import cn.wnhyang.coolguard.decision.fun.LogicOp;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author wnhyang
 * @date 2024/5/16
 **/
@Slf4j
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

    public LogicOp<String> stringLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        case CONTAINS -> a.contains(b);
        case NOT_CONTAINS -> !a.contains(b);
        case PREFIX -> a.startsWith(b);
        case NOT_PREFIX -> !a.startsWith(b);
        case SUFFIX -> a.endsWith(b);
        case NOT_SUFFIX -> !a.endsWith(b);
        default -> false;
    };

    public LogicOp<Integer> integerLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        case LT -> a < b;
        case LTE -> a <= b;
        case GT -> a > b;
        case GTE -> a >= b;
        default -> false;
    };

    public LogicOp<Double> doubleLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        case LT -> a < b;
        case LTE -> a <= b;
        case GT -> a > b;
        case GTE -> a >= b;
        default -> false;
    };

    public LogicOp<LocalDateTime> dateLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        case LT -> a.isBefore(b);
        case LTE -> a.isBefore(b) || a.equals(b);
        case GT -> a.isAfter(b);
        case GTE -> a.isAfter(b) || a.equals(b);
        default -> false;
    };

    public LogicOp<String> enumLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        default -> false;
    };

    public LogicOp<Boolean> booleanLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case NULL -> ObjUtil.isNull(a);
        case NOT_NULL -> ObjUtil.isNotNull(a);
        case EQ -> a.equals(b);
        case NOT_EQ -> !a.equals(b);
        default -> false;
    };

    public LogicOp<String> regularLogicOp = (a, logicType, b) -> switch (Objects.requireNonNull(logicType)) {
        case MATCH -> ReUtil.isMatch(b, a);
        case MATCH_IGNORE_CASE -> ReUtil.isMatch("(?i)" + b, a);
        case NOT_MATCH -> !ReUtil.isMatch(b, a);
        case NOT_MATCH_IGNORE_CASE -> !ReUtil.isMatch("(?i)" + b, a);
        default -> false;
    };


}
