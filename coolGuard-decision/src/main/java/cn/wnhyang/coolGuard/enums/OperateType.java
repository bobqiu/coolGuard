package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
@AllArgsConstructor
@Getter
public enum OperateType {

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
    NOT_SUFFIX("not_suffix");

    private final String type;

    public static OperateType getByType(String type) {
        for (OperateType operateType : OperateType.values()) {
            if (operateType.getType().equals(type)) {
                return operateType;
            }
        }
        return null;
    }
}
