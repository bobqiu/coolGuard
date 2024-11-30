package cn.wnhyang.coolGuard.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 策略版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicyVersionExtVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 规则列表
     */
    private List<RuleVO> ruleList;
}
