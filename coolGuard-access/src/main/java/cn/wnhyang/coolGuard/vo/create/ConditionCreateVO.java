package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ConditionCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条件类型
     */
    private String type;

    /**
     * 操作对象
     */
    private String value;

    /**
     * 操作类型
     */
    private String logicType;

    /**
     * 期望类型
     */
    private String expectType;

    /**
     * 期望值
     */
    private String expectValue;

    /**
     * 描述
     */
    private String description;
}
