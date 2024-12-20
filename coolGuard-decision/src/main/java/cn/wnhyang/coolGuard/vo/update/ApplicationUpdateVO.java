package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.ApplicationBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class ApplicationUpdateVO extends ApplicationBaseVO {

    @Serial
    private static final long serialVersionUID = -6790999572851210970L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
