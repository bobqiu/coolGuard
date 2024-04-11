package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
