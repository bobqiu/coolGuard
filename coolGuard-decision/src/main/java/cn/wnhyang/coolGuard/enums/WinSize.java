package cn.wnhyang.coolGuard.enums;

import cn.wnhyang.coolGuard.entity.NameValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    SECOND("秒", "s", 1L),

    /**
     * 分钟
     */
    MINUTE("分", "m", 60L),

    /**
     * 小时
     */
    HOUR("时", "H", 60 * 60L),

    /**
     * 天
     */
    DAY("天", "d", 24 * 60 * 60L),

    /**
     * 月
     */
    MONTH("月", "M", 30 * 24 * 60 * 60L);

    private final String name;

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

    public static List<NameValue> getNvList() {
        return Arrays.stream(values())
                .map(winSize -> new NameValue(winSize.getName(), winSize.getSize()))
                .collect(Collectors.toList());
    }

}
