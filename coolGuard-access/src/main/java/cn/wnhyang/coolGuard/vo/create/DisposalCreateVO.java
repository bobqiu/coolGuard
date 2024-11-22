package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.DisposalBaseVO;
import jakarta.validation.constraints.NotBlank;
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
    private String code;

    /**
     * 处置名
     */
    @NotBlank(message = "处置名不能为空")
    private String name;
}
