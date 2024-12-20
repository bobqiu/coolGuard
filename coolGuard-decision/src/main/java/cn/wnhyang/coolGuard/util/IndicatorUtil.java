package cn.wnhyang.coolGuard.util;

import cn.wnhyang.coolGuard.enums.FieldType;

import java.util.Objects;

/**
 * @author wnhyang
 * @date 2024/11/14
 **/
public class IndicatorUtil {

    public static String getReturnType(String type, String calcField) {
        return switch (type) {
            case "his" -> Objects.requireNonNull(FieldType.getByFieldName(calcField)).getType();
            default -> "F";
        };
    }
}
