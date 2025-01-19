package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Data
public class FieldRefUpdateVO extends FieldRefCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
