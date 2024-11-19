package cn.wnhyang.coolGuard.enums;

import cn.wnhyang.coolGuard.entity.NameValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
@AllArgsConstructor
@Getter
public enum LogicType {

    NULL("空", "null"),
    NOT_NULL("非空", "not_null"),
    EQ("等于", "eq"),
    NOT_EQ("不等于", "not_eq"),
    GT("大于", "gt"),
    GTE("大于等于", "gte"),
    LT("小于", "lt"),
    LTE("小于等于", "lte"),
    CONTAINS("包含", "contains"),
    NOT_CONTAINS("不包含", "not_contains"),
    PREFIX("前缀", "prefix"),
    NOT_PREFIX("非前缀", "not_prefix"),
    SUFFIX("后缀", "suffix"),
    NOT_SUFFIX("非后缀", "not_suffix"),

    /**
     * 适用正则、名单条件
     */
    MATCH("正则匹配", "match"),

    /**
     * 适用正则、名单条件
     */
    NOT_MATCH("非正则匹配", "not_match"),

    /**
     * 适用正则条件
     */
    MATCH_IGNORE_CASE("正则匹配忽略大小写", "match_ignore_case"),

    /**
     * 适用正则条件
     */
    NOT_MATCH_IGNORE_CASE("非正则匹配忽略大小写", "not_match_ignore_case");

    private final String name;

    private final String type;

    public static LogicType getByType(String type) {
        for (LogicType logicType : LogicType.values()) {
            if (logicType.getType().equals(type)) {
                return logicType;
            }
        }
        return null;
    }

    public static List<NameValue> getNvList(String fieldType) {
        return switch (fieldType) {
            // 字符，支持【等于、不等于、包含、不包含、前缀、非前缀、后缀、非后缀、为空、不为空】
            case "S" -> List.of(
                    getNv(NULL),
                    getNv(NOT_NULL),
                    getNv(EQ),
                    getNv(NOT_EQ),
                    getNv(CONTAINS),
                    getNv(NOT_CONTAINS),
                    getNv(PREFIX),
                    getNv(NOT_PREFIX),
                    getNv(SUFFIX),
                    getNv(NOT_SUFFIX));
            // 整数/小数/日期，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
            case "N", "F", "D" -> List.of(
                    getNv(NULL),
                    getNv(NOT_NULL),
                    getNv(EQ),
                    getNv(NOT_EQ),
                    getNv(GT),
                    getNv(GTE),
                    getNv(LT),
                    getNv(LTE));
            // 枚举/布尔，支持【等于、不等于、为空、不为空】
            case "E", "B" -> List.of(
                    getNv(NULL),
                    getNv(NOT_NULL),
                    getNv(EQ),
                    getNv(NOT_EQ));
            default -> List.of(
                    getNv(NULL),
                    getNv(NOT_NULL));
        };
    }

    public static NameValue getNv(LogicType logicType) {
        return new NameValue(logicType.getName(), logicType.getType());
    }
}
