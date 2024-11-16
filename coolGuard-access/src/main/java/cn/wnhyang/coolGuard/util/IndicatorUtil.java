package cn.wnhyang.coolGuard.util;

import cn.hutool.core.util.IdUtil;
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

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            String uuid = IdUtil.fastSimpleUUID();
            System.out.println(uuid);
        }
    }
}
