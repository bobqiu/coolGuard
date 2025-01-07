package cn.wnhyang.coolGuard.system.vo.param;

import lombok.Data;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamVO extends ParamCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    private Long id;
}
