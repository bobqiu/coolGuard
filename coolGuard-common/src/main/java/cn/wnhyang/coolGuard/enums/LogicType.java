package cn.wnhyang.coolGuard.enums;

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

    NULL("null"),
    NOT_NULL("not_null"),
    EQ("eq"),
    NOT_EQ("not_eq"),
    GT("gt"),
    GTE("gte"),
    LT("lt"),
    LTE("lte"),
    CONTAINS("contains"),
    NOT_CONTAINS("not_contains"),
    PREFIX("prefix"),
    NOT_PREFIX("not_prefix"),
    SUFFIX("suffix"),
    NOT_SUFFIX("not_suffix"),

    /**
     * 适用正则、名单条件
     */
    MATCH("match"),

    /**
     * 适用正则、名单条件
     */
    NOT_MATCH("not_match"),

    /**
     * 适用正则条件
     */
    MATCH_IGNORE_CASE("match_ignore_case"),

    /**
     * 适用正则条件
     */
    NOT_MATCH_IGNORE_CASE("not_match_ignore_case");

    private final String type;

    public static LogicType getByType(String type) {
        for (LogicType logicType : LogicType.values()) {
            if (logicType.getType().equals(type)) {
                return logicType;
            }
        }
        return null;
    }

    public static List<String> getTypeList(String fieldType) {
        return switch (fieldType) {
            // 字符，支持【等于、不等于、包含、不包含、前缀、非前缀、后缀、非后缀、为空、不为空】
            case "S" ->
                    List.of("null", "not_null", "eq", "not_eq", "contains", "not_contains", "prefix", "not_prefix", "suffix", "not_suffix");
            // 整数/小数/日期，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
            case "N", "F", "D" -> List.of("null", "not_null", "eq", "not_eq", "gt", "gte", "lt", "lte");
            // 枚举/布尔，支持【等于、不等于、为空、不为空】
            case "E", "B" -> List.of("null", "not_null", "eq", "not_eq");
            default -> List.of("null", "not_null");
        };
    }
}
