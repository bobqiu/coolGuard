package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.PolicyBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicyCreateVO extends PolicyBaseVO {

    @Serial
    private static final long serialVersionUID = 2093290268962978058L;

    /**
     * 策略集id
     */
    private Long policySetId;

    /**
     * 策略编码
     */
    private String code;

    /**
     * 策略模式
     */
    private String mode;

}
