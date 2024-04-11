package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.RuleCreateVO;
import lombok.Data;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleUpdateVO extends RuleCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
