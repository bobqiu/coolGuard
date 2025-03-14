package cn.wnhyang.coolguard.decision.fun;

import cn.wnhyang.coolguard.decision.enums.LogicType;

/**
 * @author wnhyang
 * @date 2024/6/14
 **/
@FunctionalInterface
public interface LogicOp<T> {

    boolean apply(T a, LogicType logicType, T b);
}
