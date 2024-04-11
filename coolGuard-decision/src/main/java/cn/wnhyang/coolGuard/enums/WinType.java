package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
@AllArgsConstructor
@Getter
public enum WinType {

    /**
     * 最近
     */
    LAST("last"),

    /**
     * 本
     */
    CUR("cur");


    private final String type;
}
