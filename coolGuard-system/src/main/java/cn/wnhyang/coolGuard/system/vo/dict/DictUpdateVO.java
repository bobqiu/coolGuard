package cn.wnhyang.coolGuard.system.vo.dict;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
public class DictUpdateVO extends DictCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @NotNull(message = "字典id不能为空")
    private Long id;
}
