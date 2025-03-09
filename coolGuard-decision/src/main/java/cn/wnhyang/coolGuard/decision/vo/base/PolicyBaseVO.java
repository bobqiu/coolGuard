package cn.wnhyang.coolGuard.decision.vo.base;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class PolicyBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7548106664553347287L;

    /**
     * 策略名
     */
    @NotBlank(message = "策略名不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;
}
