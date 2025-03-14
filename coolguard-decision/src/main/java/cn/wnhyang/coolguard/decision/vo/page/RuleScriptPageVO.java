package cn.wnhyang.coolguard.decision.vo.page;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
