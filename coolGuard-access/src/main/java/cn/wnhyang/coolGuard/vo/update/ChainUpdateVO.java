package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.ChainCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainUpdateVO extends ChainCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
