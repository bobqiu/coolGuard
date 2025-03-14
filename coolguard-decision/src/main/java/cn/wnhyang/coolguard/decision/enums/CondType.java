package cn.wnhyang.coolguard.decision.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/11/19
 **/
@AllArgsConstructor
@Getter
public enum CondType {

    NORMAL("普通条件", "normal"),
    ZB("指标条件", "zb"),
    REGULAR("正则条件", "regular"),
    LIST("名单条件", "list"),
    SCRIPT("脚本条件", "script");

    private final String name;

    private final String type;

    public static CondType getByType(String type) {
        for (CondType condType : CondType.values()) {
            if (condType.getType().equals(type)) {
                return condType;
            }
        }
        return NORMAL;
    }
}
