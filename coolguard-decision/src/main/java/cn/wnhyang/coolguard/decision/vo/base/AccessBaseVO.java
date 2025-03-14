package cn.wnhyang.coolguard.decision.vo.base;

import cn.wnhyang.coolguard.decision.entity.ParamConfig;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class AccessBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5565734558672093346L;

    /**
     * 接入名
     */
    @NotBlank(message = "接入名不能为空")
    @Size(min = 1, max = 50, message = "接入名长度必须在1-50之间")
    private String name;

    /**
     * 输入配置
     */
    private List<ParamConfig> inputFieldList;

    /**
     * 输出配置
     */
    private List<ParamConfig> outputFieldList;

    /**
     * 描述
     */
    private String description;
}
