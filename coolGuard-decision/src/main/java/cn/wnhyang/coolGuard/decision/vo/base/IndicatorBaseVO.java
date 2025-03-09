package cn.wnhyang.coolGuard.decision.vo.base;

import cn.wnhyang.coolGuard.decision.entity.Cond;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/7/18
 **/
@Data
public class IndicatorBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6202170946864429925L;

    /**
     * 指标名
     */
    @NotBlank(message = "指标名不能为空")
    @Size(min = 1, max = 50, message = "指标名长度必须在1-50之间")
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 条件
     */
    private Cond cond;

}
