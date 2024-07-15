package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.DisposalBaseVO;
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
    private Long id;
}
