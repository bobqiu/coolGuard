package cn.wnhyang.coolguard.decision.vo.create;

import cn.wnhyang.coolguard.decision.vo.base.FieldGroupBaseVO;
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
public class FieldGroupCreateVO extends FieldGroupBaseVO {

    @Serial
    private static final long serialVersionUID = -150564966296898687L;

    /**
     * 字段分组编码
     */
    @NotBlank(message = "字段分组编码不能为空")
    @Size(min = 1, max = 30, message = "字段分组编码长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "字段分组编码只能包含字母和数字")
    private String code;

}
