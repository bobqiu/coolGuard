package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.StrategySetCreateVO;
import lombok.Data;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategySetUpdateVO extends StrategySetCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
