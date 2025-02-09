package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.IndicatorBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorCreateVO extends IndicatorBaseVO {

    @Serial
    private static final long serialVersionUID = 1380185008381630457L;

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
     * earliest latest
     * 返回取值方式
     */
    private String returnFlag;

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
    private List<String> slaveFields;

    /**
     * 过滤脚本
     */
    private String filterScript;

    /**
     * 场景
     */
    @NotBlank(message = "场景不能为空")
    private List<String> scenes;

    /**
     * 场景类型
     */
    @NotBlank(message = "场景类型不能为空")
    private String sceneType;

}
