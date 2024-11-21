package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 指标表历史表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionBaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    private String code;

    /**
     * 指标名
     */
    private String name;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 类型
     */
    private String type;

    /**
     * 计算字段
     */
    private String calcField;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 返回取值方式
     */
    private String returnFlag;

    /**
     * 窗口大小
     */
    private String winSize;

    /**
     * 窗口类型
     */
    private String winType;

    /**
     * 窗口数量
     */
    private Integer winCount;

    /**
     * 时间片
     */
    private Long timeSlice;

    /**
     * 主字段
     */
    private String masterField;

    /**
     * 从字段
     */
    private String slaveFields;

    /**
     * 计算脚本
     */
    private String computeScript;

    /**
     * 场景（,分割）
     */
    private String scenes;

    /**
     * 场景类型
     */
    private String sceneType;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 条件
     */
    private String cond;
}
