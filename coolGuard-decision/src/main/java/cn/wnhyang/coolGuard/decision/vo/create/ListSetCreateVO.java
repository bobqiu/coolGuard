package cn.wnhyang.coolGuard.decision.vo.create;

import cn.wnhyang.coolGuard.decision.vo.base.ListSetBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetCreateVO extends ListSetBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名单集编码
     */
    @NotBlank(message = "名单集编码不能为空")
    @Size(min = 1, max = 30, message = "名单集编码长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "名单集编码只能包含字母和数字")
    private String code;

}
