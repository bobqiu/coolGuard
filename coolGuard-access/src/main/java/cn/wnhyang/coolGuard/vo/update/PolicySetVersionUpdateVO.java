package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicySetVersionUpdateVO extends PolicySetVersionCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
