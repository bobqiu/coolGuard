package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.vo.create.ChainCreateVO;
import lombok.Data;

import java.io.Serial;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainVO extends ChainCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
