package cn.wnhyang.coolguard.decision.vo.update;

import cn.wnhyang.coolguard.decision.vo.base.DisposalBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalUpdateVO extends DisposalBaseVO {

    @Serial
    private static final long serialVersionUID = 4214406194053463597L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
