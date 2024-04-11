package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.RuleScriptCreateVO;
import lombok.Data;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptVO extends RuleScriptCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
