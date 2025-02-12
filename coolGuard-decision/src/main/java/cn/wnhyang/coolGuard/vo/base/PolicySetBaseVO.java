package cn.wnhyang.coolGuard.vo.base;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class PolicySetBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -267362598649710050L;

    /**
     * 策略集名
     */
    @NotBlank(message = "策略集名不能为空")
    private String name;

    /**
     * 策略集链
     */
    @NotBlank(message = "策略集链不能为空")
    private String chain;

    /**
     * 描述
     */
    private String description;

}
