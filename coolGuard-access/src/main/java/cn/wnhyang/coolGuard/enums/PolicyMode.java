package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/4/6
 **/
@AllArgsConstructor
@Getter
public enum PolicyMode {

    ORDER("order"),
    WORST("worst"),
    WEIGHT("weight");

    private final String mode;

    public static PolicyMode getByMode(String mode) {
        for (PolicyMode value : PolicyMode.values()) {
            if (value.getMode().equals(mode)) {
                return value;
            }
        }
        return null;
    }
}
