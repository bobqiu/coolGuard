package cn.wnhyang.coolguard.system.vo.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamUpdateVO extends ParamCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    @NotNull(message = "参数id不能为空")
    private Long id;
}
