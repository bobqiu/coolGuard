package cn.wnhyang.coolguard.decision.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/7/27
 **/
@Data
public class Cond implements Serializable {

    @Serial
    private static final long serialVersionUID = -1831587613757992692L;

    /**
     * AND｜OR｜NOT
     */
    private String relation;

    private List<Cond> children;

    /**
     * 条件类型
     */
    private String type;

    /**
     * 操作对象
     */
    private String leftValue;

    /**
     * 操作类型
     */
    private String logicType;

    /**
     * 期望类型
     */
    private String rightType;

    /**
     * 期望值
     */
    private String rightValue;

}
