package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -4138518261130779757L;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 字段名
     */
    private String name;

}
