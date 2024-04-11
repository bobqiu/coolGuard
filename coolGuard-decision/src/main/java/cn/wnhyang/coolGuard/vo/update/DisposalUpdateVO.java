package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.DisposalCreateVO;
import lombok.Data;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalUpdateVO extends DisposalCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
