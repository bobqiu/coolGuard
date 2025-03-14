package cn.wnhyang.coolguard.decision.vo.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class FieldGroupBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7067761906255913463L;

    /**
     * 字段分组名
     */
    @NotBlank(message = "字段分组名不能为空")
    @Size(min = 1, max = 50, message = "字段分组名长度必须在1-50之间")
    private String name;

    /**
     * 描述
     */
    private String description;

}
