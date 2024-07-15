package cn.wnhyang.coolGuard.vo.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorCreateVO {

    /**
     * 指标名
     */
    @NotBlank(message = "指标名不能为空")
    @Size(min = 1, max = 50, message = "指标名长度必须在1-50之间")
    private String name;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * chain名
     */
    private String chainName;

    /**
     * 类型
     */
    @NotBlank(message = "指标类型不能为空")
    @Size(min = 1, max = 10, message = "指标类型长度必须在1-10之间")
    private String type;

    /**
     * 计算字段
     */
    private String calcField;

    /**
     * 窗口大小
     */
    @NotBlank(message = "窗口大小不能为空")
    private String winSize;

    /**
     * 窗口类型
     */
    @NotBlank(message = "窗口类型不能为空")
    private String winType;

    /**
     * 窗口数量
     */
    @NotNull(message = "窗口数量不能为空")
    private Integer winCount;

    /**
     * 主字段
     */
    @NotBlank(message = "主字段不能为空")
    private String masterField;

    /**
     * 从字段
     */
    @NotBlank(message = "从字段不能为空")
    private String slaveFields;

    /**
     * 过滤脚本
     */
    private String filterScript;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 场景
     */
    private String scene;

    /**
     * 场景类型
     */
    private String sceneType;

    /**
     * 描述
     */
    private String description;
}
