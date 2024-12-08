package cn.wnhyang.coolGuard.vo.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/12/5
 **/
@Data
public class PolicySetChainUpdateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2514641647380036051L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 策略集链
     */
    @NotBlank(message = "策略集链不能为空")
    private String chain;
}
