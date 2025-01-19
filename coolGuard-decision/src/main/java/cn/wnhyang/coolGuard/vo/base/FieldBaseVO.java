package cn.wnhyang.coolGuard.vo.base;

import cn.wnhyang.coolGuard.entity.LabelValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/12/19
 **/
@Data
public class FieldBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5431692545506779115L;

    /**
     * 显示名
     */
    @NotBlank(message = "字段显示名不能为空")
    @Size(min = 1, max = 50, message = "字段显示名长度必须在1-50之间")
    private String name;

    /**
     * 字段分组
     */
    @NotBlank(message = "字段分组名不能为空")
    private String groupCode;

    /**
     * 字段信息
     */
    private List<LabelValue> info;

    /**
     * 描述
     */
    private String description;

    /**
     * 默认值
     */
    @NotBlank(message = "字段默认值不能为空")
    private String defaultValue;

    /**
     * 动态字段脚本
     */
    private String script;
}
