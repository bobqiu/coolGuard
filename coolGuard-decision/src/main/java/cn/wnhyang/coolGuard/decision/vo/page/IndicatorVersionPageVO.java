package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 指标表版本表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 指标编码
     */
    private String code;

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
