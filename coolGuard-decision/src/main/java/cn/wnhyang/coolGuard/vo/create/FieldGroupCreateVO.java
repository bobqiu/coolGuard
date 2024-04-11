package cn.wnhyang.coolGuard.vo.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldGroupCreateVO {

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 显示分组名
     */
    @NotBlank(message = "分组名不能为空")
    @Size(min = 1, max = 50, message = "分组名长度必须在1-50之间")
    private String displayName;

    /**
     * 分组标识
     */
    @NotBlank(message = "分组标识不能为空")
    @Size(min = 1, max = 30, message = "分组标识长度必须在1-30之间")
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 组内字段数
     */
    private Integer count;
}
