package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    MATCH("match"),
    NOT_MATCH("not_match"),
    MATCH_IGNORE_CASE("match_ignore_case"),
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
}
