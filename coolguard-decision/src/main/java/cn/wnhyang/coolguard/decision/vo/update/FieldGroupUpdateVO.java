package cn.wnhyang.coolguard.decision.vo.update;

import cn.wnhyang.coolguard.decision.vo.base.FieldGroupBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldGroupUpdateVO extends FieldGroupBaseVO {

    @Serial
    private static final long serialVersionUID = -853798486468598094L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
