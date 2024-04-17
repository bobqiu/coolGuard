package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.StrategyBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class StrategyUpdateVO extends StrategyBaseVO {

    @Serial
    private static final long serialVersionUID = -4291651620526104689L;

    /**
     * 主键
     */
    private Long id;
}
