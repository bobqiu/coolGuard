package cn.wnhyang.coolGuard.decision.vo.create;

import cn.wnhyang.coolGuard.decision.vo.base.RuleBaseVO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleCreateVO extends RuleBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 策略编码
     */
    @NotBlank(message = "策略编码不能为空")
    private String policyCode;

}
