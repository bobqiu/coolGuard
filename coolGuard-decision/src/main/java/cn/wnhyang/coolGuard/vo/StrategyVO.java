package cn.wnhyang.coolGuard.vo;

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
public class StrategyVO extends StrategyBaseVO {

    @Serial
    private static final long serialVersionUID = -8532588059860661308L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略集id
     */
    private Long strategySetId;

    /**
     * 策略编码
     */
    private String code;

    /**
     * 策略模式
     */
    private String mode;
}
