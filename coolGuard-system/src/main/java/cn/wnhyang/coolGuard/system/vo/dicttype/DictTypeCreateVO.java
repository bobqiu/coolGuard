package cn.wnhyang.coolGuard.system.vo.dicttype;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2023/9/13
 **/
@Data
public class DictTypeCreateVO extends DictTypeBaseVO {

    @NotNull(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String code;
}
