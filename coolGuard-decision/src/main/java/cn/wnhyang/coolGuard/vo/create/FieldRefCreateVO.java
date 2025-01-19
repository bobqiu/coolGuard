package cn.wnhyang.coolGuard.vo.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Data
public class FieldRefCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 被引用类型
     */
    @NotBlank(message = "被引用类型不能为空")
    private String refType;

    /**
     * 被引用
     */
    @NotBlank(message = "被引用不能为空")
    private String refBy;

    /**
     * 被引用子类型
     */
    @NotBlank(message = "被引用子类型不能为空")
    private String refSubType;

    /**
     * 字段编码
     */
    @NotBlank(message = "被引用字段不能为空")
    private String fieldCode;

    /**
     * 必须
     */
    private Boolean required;

    /**
     * 参数名
     */
    private String paramName;
}
