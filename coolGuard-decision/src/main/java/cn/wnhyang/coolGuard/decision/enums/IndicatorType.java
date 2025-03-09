package cn.wnhyang.coolGuard.decision.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

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

    /**
     * 根据类型和计算字段获取返回类型
     *
     * @param type      指标类型
     * @param calcField 计算字段
     * @return 返回值类型
     */
    public static String getReturnType(String type, String calcField) {
        return switch (type) {
            // 次数统计，关联次数，返回值为整数
            case "count", "ass" -> "N";
            // 历史取值，返回值为计算字段类型
            case "his" -> Objects.requireNonNull(FieldType.getByFieldCode(calcField)).getType();
            // 求和，平均，最大值，最小值，返回值为浮点数
            default -> "F";
            // TODO 移动距离、移动速度
        };
    }
}
