package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.RuleBaseVO;
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
    private Long id;

}
