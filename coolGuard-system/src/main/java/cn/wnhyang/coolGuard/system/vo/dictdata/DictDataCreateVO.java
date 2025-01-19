package cn.wnhyang.coolGuard.system.vo.dictdata;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * @author wnhyang
 * @date 2023/9/14
 **/
@Data
public class DictDataCreateVO {

    private Integer sort;

    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String name;

    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String code;

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String typeCode;

    private String color;

    @NotNull(message = "状态不能为空")
    private Boolean status;

    private String remark;
}
