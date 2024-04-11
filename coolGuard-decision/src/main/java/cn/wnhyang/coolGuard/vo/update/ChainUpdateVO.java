package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.ChainCreateVO;
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
    private Long id;
}
