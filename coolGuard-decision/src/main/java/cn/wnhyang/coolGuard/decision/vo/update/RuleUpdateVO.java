package cn.wnhyang.coolGuard.decision.vo.update;

import cn.wnhyang.coolGuard.decision.vo.base.RuleBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleUpdateVO extends RuleBaseVO {

    @Serial
    private static final long serialVersionUID = -8130415488267597266L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

}
