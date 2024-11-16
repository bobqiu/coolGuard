package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.PolicySetBaseVO;
import jakarta.validation.constraints.NotBlank;
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
    private String appName;

    /**
     * 策略集编码
     */
    @NotBlank(message = "策略集编码不能为空")
    private String code;

}
