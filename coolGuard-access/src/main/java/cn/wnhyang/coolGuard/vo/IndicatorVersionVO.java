package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.IndicatorVersionBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 指标表版本表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionVO extends IndicatorVersionBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    private Long id;
}
