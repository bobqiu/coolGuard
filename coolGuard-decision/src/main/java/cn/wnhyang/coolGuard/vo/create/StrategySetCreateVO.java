package cn.wnhyang.coolGuard.vo.create;

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
public class StrategySetCreateVO extends StrategySetBaseVO {

    @Serial
    private static final long serialVersionUID = -7577814391272820228L;

    /**
     * app名
     */
    private String appName;

    /**
     * 策略集编码
     */
    private String code;

}
