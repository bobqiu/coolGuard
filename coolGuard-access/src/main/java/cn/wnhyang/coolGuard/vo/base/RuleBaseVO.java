package cn.wnhyang.coolGuard.vo.base;

import cn.wnhyang.coolGuard.entity.Cond;
import cn.wnhyang.coolGuard.entity.RuleBingo;
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
     * 规则编码
     */
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "规则编码只能包含字母和数字")
    @Size(min = 6, max = 10, message = "规则编码长度为6-10位")
    @NotBlank(message = "规则编码不能为空")
    private String code;

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
     * 分数
     */
    private Integer score;

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
    private RuleBingo ruleTrue;

    /**
     * false执行
     */
    private RuleBingo ruleFalse;

}
