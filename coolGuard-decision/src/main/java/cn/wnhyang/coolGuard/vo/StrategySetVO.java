package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.StrategySetBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategySetVO extends StrategySetBaseVO {

    @Serial
    private static final long serialVersionUID = -3067674637266164651L;

    /**
     * 主键
     */
    private Long id;

    /**
     * app名
     */
    private String appName;

    /**
     * 策略集编码
     */
    private String code;
}
