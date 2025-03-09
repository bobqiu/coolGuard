package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RulePageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 策略编码
     */
    private String policyCode;

    /**
     * 规则id
     */
    private String ruleId;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 规则名
     */
    private String name;

    /**
     * 处理编码
     */
    private String disposalCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 有版本号
     */
    private Boolean hasVersion;
}
