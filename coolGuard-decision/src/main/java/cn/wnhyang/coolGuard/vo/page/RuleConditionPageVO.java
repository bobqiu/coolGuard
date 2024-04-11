package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleConditionPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
