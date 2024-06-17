package cn.wnhyang.coolGuard.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
@AllArgsConstructor
@Getter
public enum FieldType {

    /**
     * 字符型，支持【等于、不等于、包含、不包含、前缀、非前缀、后缀、非后缀、为空、不为空、存在于、不存在于】
     */
    STRING("S"),

    /**
     * 整数型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空、存在于，不存在于】
     */
    NUMBER("N"),

    /**
     * 小数型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
     */
    FLOAT("F"),

    /**
     * 日期型，支持【等于、不等于、大于、小于、大于等于、小于等于、为空、不为空】
     */
    DATE("D"),

    /**
     * 枚举型，支持【等于、不等于、为空、不为空】
     */
    ENUM("E"),

    /**
     * 布尔型，支持【等于、不等于、为空、不为空】
     */
    BOOLEAN("B");

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
}
