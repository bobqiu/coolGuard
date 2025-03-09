package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
