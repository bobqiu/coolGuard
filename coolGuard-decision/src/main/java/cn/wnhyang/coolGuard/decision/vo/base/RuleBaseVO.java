package cn.wnhyang.coolGuard.decision.vo.base;

import cn.wnhyang.coolGuard.decision.entity.Action;
import cn.wnhyang.coolGuard.decision.entity.Cond;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/7/28
 **/
@Data
public class RuleBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4420969179975300529L;

    /**
     * 规则id
     */
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "规则编码只能包含字母和数字")
    @Size(min = 6, max = 6, message = "规则编码长度为6-10位")
    @NotBlank(message = "规则编码不能为空")
    private String ruleId;

    /**
     * 规则名
     */
    @NotBlank(message = "规则名不能为空")
    private String name;

    /**
     * 处理编码
     */
    @NotBlank(message = "处理编码不能为空")
    private String disposalCode;

    /**
     * 表达式
     */
    private String express;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    /**
     * 条件
     */
    private Cond cond;

    /**
     * true执行
     */
    private Action ruleTrue;

    /**
     * false执行
     */
    private Action ruleFalse;

}
