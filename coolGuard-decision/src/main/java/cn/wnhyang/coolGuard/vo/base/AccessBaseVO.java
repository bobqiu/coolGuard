package cn.wnhyang.coolGuard.vo.base;

import cn.wnhyang.coolGuard.entity.ConfigField;
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
     * 显示服务名
     */
    @NotBlank(message = "字段显示名不能为空")
    @Size(min = 1, max = 50, message = "字段显示名长度必须在1-50之间")
    private String name;

    /**
     * 输入配置
     */
    private List<ConfigField> inputConfig;

    /**
     * 输出配置
     */
    private List<ConfigField> outputConfig;

    /**
     * 描述
     */
    private String description;
}
