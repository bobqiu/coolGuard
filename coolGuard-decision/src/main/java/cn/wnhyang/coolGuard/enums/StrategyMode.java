package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/4/6
 **/
@AllArgsConstructor
@Getter
public enum StrategyMode {

    ORDER("order"),
    WORST("worst"),
    WEIGHT("weight");

    private final String mode;

    public static StrategyMode getByMode(String mode) {
        for (StrategyMode value : StrategyMode.values()) {
            if (value.getMode().equals(mode)) {
                return value;
            }
        }
        return null;
    }
}
