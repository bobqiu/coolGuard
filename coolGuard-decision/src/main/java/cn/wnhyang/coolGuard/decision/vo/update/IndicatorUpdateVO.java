package cn.wnhyang.coolGuard.decision.vo.update;

import cn.wnhyang.coolGuard.decision.vo.base.IndicatorBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorUpdateVO extends IndicatorBaseVO {

    @Serial
    private static final long serialVersionUID = -5702755560162667040L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
