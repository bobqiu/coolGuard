package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.StrategyCreateVO;
import lombok.Data;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategyUpdateVO extends StrategyCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
