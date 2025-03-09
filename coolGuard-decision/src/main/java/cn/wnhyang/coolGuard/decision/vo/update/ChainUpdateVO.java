package cn.wnhyang.coolGuard.decision.vo.update;

import cn.wnhyang.coolGuard.decision.vo.create.ChainCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainUpdateVO extends ChainCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
