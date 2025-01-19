package cn.wnhyang.coolGuard.system.vo.dicttype;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/9/13
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeUpdateVO extends DictTypeBaseVO {

    @NotNull(message = "字典类型编号不能为空")
    private Long id;
}
