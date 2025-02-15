package cn.wnhyang.coolGuard.fun;

import cn.wnhyang.coolGuard.enums.LogicType;

/**
 * @author wnhyang
 * @date 2024/6/14
 **/
@FunctionalInterface
public interface LogicOp<T> {

    boolean apply(T a, LogicType logicType, T b);
}
