package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.FieldGroupBaseVO;
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
     * 分组标识
     */
    @NotBlank(message = "分组标识不能为空")
    @Size(min = 1, max = 30, message = "分组标识长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "分组标识只能包含字母和数字")
    private String name;

}
