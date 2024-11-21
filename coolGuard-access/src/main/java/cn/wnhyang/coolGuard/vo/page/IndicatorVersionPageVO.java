package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 指标表历史表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 指标名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 场景
     */
    private String scene;

    /**
     * 场景类型
     */
    private String sceneType;
}
