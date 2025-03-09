package cn.wnhyang.coolGuard.decision.vo.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class DisposalBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2853793969240349334L;

    /**
     * 处置名
     */
    @NotBlank(message = "处置名不能为空")
    private String name;

    /**
     * 等级
     */
    @NotNull(message = "等级不能为空")
    private Integer grade;

    /**
     * 颜色
     */
    @NotNull(message = "颜色不能为空")
    private String color;

    /**
     * 描述
     */
    private String description;

}
