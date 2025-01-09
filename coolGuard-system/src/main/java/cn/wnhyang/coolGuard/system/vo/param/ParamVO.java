package cn.wnhyang.coolGuard.system.vo.param;

import lombok.Data;

import java.io.Serial;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamVO extends ParamCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    private Long id;

    /**
     * 标准
     */
    private Boolean standard;
}
