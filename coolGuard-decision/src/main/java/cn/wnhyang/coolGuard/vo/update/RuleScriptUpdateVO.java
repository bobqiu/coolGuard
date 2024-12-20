package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.RuleScriptCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptUpdateVO extends RuleScriptCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
