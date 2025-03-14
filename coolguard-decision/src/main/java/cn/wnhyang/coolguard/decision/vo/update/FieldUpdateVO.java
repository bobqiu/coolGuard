package cn.wnhyang.coolguard.decision.vo.update;

import cn.wnhyang.coolguard.decision.vo.base.FieldBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldUpdateVO extends FieldBaseVO {

    @Serial
    private static final long serialVersionUID = -954983526640093521L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
