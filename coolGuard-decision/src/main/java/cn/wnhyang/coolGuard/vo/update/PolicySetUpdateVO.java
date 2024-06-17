package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.PolicySetBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicySetUpdateVO extends PolicySetBaseVO {

    @Serial
    private static final long serialVersionUID = 2944541149936060422L;

    /**
     * 主键
     */
    private Long id;
}
