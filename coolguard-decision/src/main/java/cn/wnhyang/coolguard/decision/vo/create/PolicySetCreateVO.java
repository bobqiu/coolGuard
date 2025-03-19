package cn.wnhyang.coolguard.decision.vo.create;

import cn.wnhyang.coolguard.decision.vo.base.PolicySetBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicySetCreateVO extends PolicySetBaseVO {

    @Serial
    private static final long serialVersionUID = -7577814391272820228L;

    /**
     * app名
     */
    @NotBlank(message = "应用名不能为空")
    private String appCode;

    /**
     * 策略集编码
     */
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "策略集编码只能包含字母和数字")
    @NotBlank(message = "策略集编码不能为空")
    private String code;

}
