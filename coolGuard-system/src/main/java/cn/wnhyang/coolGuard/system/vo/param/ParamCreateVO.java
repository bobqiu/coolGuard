package cn.wnhyang.coolGuard.system.vo.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数标签
     */
    @NotBlank(message = "参数标签不能为空")
    private String label;

    /**
     * 参数值
     */
    @NotBlank(message = "参数值不能为空")
    private String value;

    /**
     * 参数类型
     */
    @NotBlank(message = "参数类型不能为空")
    private String type;

    /**
     * 参数数据
     */
    @NotBlank(message = "参数数据不能为空")
    private String data;

    /**
     * 描述
     */
    private String description;
}
