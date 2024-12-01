package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.entity.Rule;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class RuleVersionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 条件
     */
    private Rule rule;

    /**
     * 策略集状态
     */
    private Boolean status;

    /**
     * 版本号
     */
    private Integer version;
}
