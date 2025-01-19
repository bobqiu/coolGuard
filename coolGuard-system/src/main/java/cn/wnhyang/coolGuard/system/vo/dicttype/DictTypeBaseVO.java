package cn.wnhyang.coolGuard.system.vo.dicttype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2025/1/18
 **/
@Data
public class DictTypeBaseVO {

    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    private String name;

    private String remark;
}
