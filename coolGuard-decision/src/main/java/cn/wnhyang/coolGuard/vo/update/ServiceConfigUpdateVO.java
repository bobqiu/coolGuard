package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.ServiceConfigBaseVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class ServiceConfigUpdateVO extends ServiceConfigBaseVO {

    @Serial
    private static final long serialVersionUID = 3216959020642419661L;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Long id;
}
