package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.entity.Th;
import cn.wnhyang.coolGuard.vo.base.PolicyBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicyCreateVO extends PolicyBaseVO {

    @Serial
    private static final long serialVersionUID = 2093290268962978058L;

    /**
     * 策略集编码
     */
    @NotBlank(message = "策略集编码不能为空")
    private String policySetCode;

    /**
     * 策略编码
     */
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "策略编码只能包含字母和数字")
    @NotBlank(message = "策略编码不能为空")
    private String code;

    /**
     * 策略模式
     */
    @NotBlank(message = "策略模式不能为空")
    private String mode;

    /**
     * 策略阈值
     */
    private List<Th> thList;

}
