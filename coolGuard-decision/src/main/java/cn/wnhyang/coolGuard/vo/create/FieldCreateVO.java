package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.FieldBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldCreateVO extends FieldBaseVO {

    @Serial
    private static final long serialVersionUID = 8827248788810403664L;

    /**
     * 字段标识
     */
    @NotBlank(message = "字段标识不能为空")
    @Size(min = 1, max = 30, message = "字段标识长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "字段标识只能包含字母和数字")
    private String code;

    /**
     * 字段类型
     */
    @NotBlank(message = "字段类型不能为空")
    private String type;

    /**
     * 是否动态字段(0否1是)
     */
    private Boolean dynamic;

}
