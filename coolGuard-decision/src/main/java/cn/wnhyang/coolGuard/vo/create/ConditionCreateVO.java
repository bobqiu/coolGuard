package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则条件表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ConditionCreateVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * chain名
     */
    private String chainName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 期望类型
     */
    private String expectedType;

    /**
     * 期望值
     */
    private String expectValue;

    /**
     * 描述
     */
    private String description;
}
