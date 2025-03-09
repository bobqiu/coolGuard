package cn.wnhyang.coolGuard.decision.vo.create;

import cn.wnhyang.coolGuard.decision.vo.base.DisposalBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalCreateVO extends DisposalBaseVO {

    @Serial
    private static final long serialVersionUID = -4401298690445309859L;

    /**
     * 处置编码
     */
    @NotBlank(message = "处置编码不能为空")
    @Size(min = 1, max = 30, message = "处置编码长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "处置编码只能包含字母和数字")
    private String code;

}
