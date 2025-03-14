package cn.wnhyang.coolguard.system.vo.dictdata;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/9/14
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataUpdateVO extends DictDataCreateVO {

    @NotNull(message = "字典数据编号不能为空")
    private Long id;
}
