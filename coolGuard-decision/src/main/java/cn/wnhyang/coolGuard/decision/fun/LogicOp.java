package cn.wnhyang.coolGuard.decision.fun;

import cn.wnhyang.coolGuard.decision.enums.LogicType;

/**
 * @author wnhyang
 * @date 2024/6/14
 **/
@FunctionalInterface
public interface LogicOp<T> {

    boolean apply(T a, LogicType logicType, T b);
}
