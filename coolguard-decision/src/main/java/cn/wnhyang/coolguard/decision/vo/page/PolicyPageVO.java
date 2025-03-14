package cn.wnhyang.coolguard.decision.vo.page;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicyPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略集编码
     */
    private String policySetCode;

    /**
     * 策略名
     */
    private String name;

    /**
     * 策略编码
     */
    private String code;

    /**
     * 策略模式
     */
    private String mode;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 有版本号
     */
    private Boolean hasVersion;
}
