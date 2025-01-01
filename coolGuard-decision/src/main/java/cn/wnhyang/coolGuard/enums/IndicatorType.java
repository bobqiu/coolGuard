package cn.wnhyang.coolGuard.enums;

import cn.wnhyang.coolGuard.entity.LabelValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    COUNT("次数", "count"),

    /**
     * 求和，计算数值类字段，返回为数字
     */
    SUM("求和", "sum"),

    /**
     * 平均，计算数值类字段，返回为数字
     */
    AVG("平均", "avg"),

    /**
     * 最大值，计算数值类字段，返回为数字
     */
    MAX("最大值", "max"),

    /**
     * 最小值，计算数值类字段，返回为数字
     */
    MIN("最小值", "min"),

    /**
     * 关联次数，计算任意字段，返回为数字
     */
    ASS("关联", "ass"),

    /**
     * 设置时需要标记取最近还是最早
     */
    HIS("历史取值", "his");

    private final String name;

    private final String type;


    public static List<LabelValue> getLvList() {
        return Arrays.stream(values())
                .map(indicatorType -> new LabelValue(indicatorType.getName(), indicatorType.getType()))
                .collect(Collectors.toList());
    }

}
