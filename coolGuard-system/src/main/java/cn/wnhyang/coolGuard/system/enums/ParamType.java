package cn.wnhyang.coolGuard.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2025/1/7
 **/
@Getter
@AllArgsConstructor
public enum ParamType {

    BOOLEAN("boolean"), STRING("string"), JSON("json");

    private final String type;

    public static ParamType getParamType(String type) {
        for (ParamType paramType : values()) {
            if (paramType.getType().equals(type)) {
                return paramType;
            }
        }
        return null;
    }
}
