package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.vo.create.RuleScriptCreateVO;
import lombok.Data;

import java.io.Serial;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptVO extends RuleScriptCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
