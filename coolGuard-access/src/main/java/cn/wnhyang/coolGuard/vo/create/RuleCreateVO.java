package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.RuleBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleCreateVO extends RuleBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 策略编码
     */
    private String policyCode;

}
