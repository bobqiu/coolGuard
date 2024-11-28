package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.entity.NameValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldCreateVO {

    /**
     * 显示名
     */
    @NotBlank(message = "字段显示名不能为空")
    @Size(min = 1, max = 50, message = "字段显示名长度必须在1-50之间")
    private String displayName;

    /**
     * 字段名
     */
    @NotBlank(message = "字段名不能为空")
    @Size(min = 1, max = 30, message = "字段名长度必须在1-30之间")
    private String name;

    /**
     * 字段分组
     */
    @NotBlank(message = "字段分组名不能为空")
    private String groupName;

    /**
     * 字段类型
     */
    @NotBlank(message = "字段类型不能为空")
    private String type;

    /**
     * 字段信息
     */
    private NameValue info;

    /**
     * 描述
     */
    @NotBlank(message = "字段描述不能为空")
    @Size(min = 1, max = 100, message = "字段描述长度必须在1-100之间")
    private String description;

    /**
     * 默认值
     */
    @NotBlank(message = "字段默认值不能为空")
    private String defaultValue;

    /**
     * 是否动态字段(0否1是)
     */
    private Boolean dynamic;

    /**
     * 动态字段脚本
     */
    private String script;
}
