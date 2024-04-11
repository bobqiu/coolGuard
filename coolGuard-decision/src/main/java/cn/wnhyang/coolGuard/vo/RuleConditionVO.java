package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.RuleConditionCreateVO;
import lombok.Data;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleConditionVO extends RuleConditionCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
