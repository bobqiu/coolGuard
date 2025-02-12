package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicySetPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * app名
     */
    private String appName;

    /**
     * 策略集编码
     */
    private String code;

    /**
     * 策略集名
     */
    private String name;

    /**
     * 策略编码
     */
    private String policyCode;

    /**
     * 策略名
     */
    private String policyName;

    /**
     * 规则编码
     */
    private String ruleCode;

    /**
     * 规则名
     */
    private String ruleName;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 有版本号
     */
    private Boolean hasVersion;
}
