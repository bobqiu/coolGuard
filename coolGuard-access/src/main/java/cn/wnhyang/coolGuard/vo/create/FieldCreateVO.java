package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.FieldBaseVO;
import jakarta.validation.constraints.NotBlank;
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
     * 字段名
     */
    @NotBlank(message = "字段名不能为空")
    @Size(min = 1, max = 30, message = "字段名长度必须在1-30之间")
    private String name;

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
