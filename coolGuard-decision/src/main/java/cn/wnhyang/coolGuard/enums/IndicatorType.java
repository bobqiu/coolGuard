package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
@AllArgsConstructor
@Getter
public enum IndicatorType {
    /**
     * 次数统计，不需要计算字段，返回为数字
     */
    COUNT("count"),

    /**
     * 求和，计算数值类字段，返回为数字
     */
    SUM("sum"),

    /**
     * 平均，计算数值类字段，返回为数字
     */
    AVG("avg"),

    /**
     * 最大值，计算数值类字段，返回为数字
     */
    MAX("max"),

    /**
     * 最小值，计算数值类字段，返回为数字
     */
    MIN("min"),

    /**
     * 关联次数，计算任意字段，返回为数字
     */
    ASS("ass");


    private final String type;
}
