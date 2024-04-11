package cn.wnhyang.coolGuard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Getter
@AllArgsConstructor
public enum WinSize {

    /**
     * 秒
     */
    SECOND("s", 1L),

    /**
     * 分钟
     */
    MINUTE("m", 60L),

    /**
     * 小时
     */
    HOUR("H", 60 * 60L),

    /**
     * 天
     */
    DAY("d", 24 * 60 * 60L),

    /**
     * 月
     */
    MONTH("M", 30 * 24 * 60 * 60L);


    private final String size;

    private final Long value;

    public static WinSize getWinSize(String size) {
        for (WinSize winSize : WinSize.values()) {
            if (winSize.size.equals(size)) {
                return winSize;
            }
        }
        return null;
    }

    public static long getWinSizeValue(String size) {
        for (WinSize winSize : WinSize.values()) {
            if (winSize.size.equals(size)) {
                return winSize.getValue();
            }
        }
        return 7776000L;
    }
}
