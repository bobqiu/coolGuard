package cn.wnhyang.coolGuard.enums;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.NameValue;
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
public enum FieldType {

    /**
     * 字符型，支持【等于、不等于、包含、不包含、前缀、非前缀、后缀、非后缀、为空、不为空】
     */
    STRING("字符串", "S"),

    /**
     * 整数型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
     */
    NUMBER("整数", "N"),

    /**
     * 小数型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
     */
    FLOAT("小数", "F"),

    /**
     * 日期型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
     */
    DATE("日期", "D"),

    /**
     * TODO 要不要保留枚举类型？
     * 枚举型，支持【等于、不等于、为空、不为空】
     */
    ENUM("枚举", "E"),

    /**
     * 布尔型，支持【等于、不等于、为空、不为空】
     */
    BOOLEAN("布尔", "B");

    private final String name;

    private final String type;

    public static FieldType getByType(String type) {
        for (FieldType value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

    public static FieldType getByFieldName(String fieldName) {
        if (fieldName.length() >= 3) {
            String sub = StrUtil.sub(fieldName, 2, 3);
            return getByType(sub);
        }
        return null;
    }

    public static List<NameValue> getNvList() {
        return Arrays.stream(values())
                .map(fieldType -> new NameValue(fieldType.getName(), fieldType.getType()))
                .collect(Collectors.toList());
    }

}
