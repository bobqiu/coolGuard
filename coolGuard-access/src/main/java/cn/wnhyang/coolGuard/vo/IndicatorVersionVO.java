package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.IndicatorVersionBaseVO;
import lombok.Data;

/**
 * 指标表历史表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionVO extends IndicatorVersionBaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    private Long id;
}
