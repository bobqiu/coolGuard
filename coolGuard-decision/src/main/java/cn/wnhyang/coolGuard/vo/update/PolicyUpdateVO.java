package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.PolicyBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicyUpdateVO extends PolicyBaseVO {

    @Serial
    private static final long serialVersionUID = -4291651620526104689L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
