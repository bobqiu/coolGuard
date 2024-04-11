package cn.wnhyang.coolGuard.vo.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class ServiceConfigCreateVO {

    /**
     * 显示服务名
     */
    @NotBlank(message = "字段显示名不能为空")
    @Size(min = 1, max = 50, message = "字段显示名长度必须在1-50之间")
    private String displayName;

    /**
     * 服务标识
     */
    @NotBlank(message = "字段名不能为空")
    @Size(min = 1, max = 30, message = "字段名长度必须在1-30之间")
    private String name;

    private List<ServiceConfigFieldCreateVO> inputFields;

    /**
     * 输入配置
     */
    private String inputConfig;

    /**
     * 输出配置
     */
    private String outputConfig;

    /**
     * 描述
     */
    private String description;
}
