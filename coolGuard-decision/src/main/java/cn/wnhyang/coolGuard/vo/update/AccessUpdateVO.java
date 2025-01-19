package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.AccessBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class AccessUpdateVO extends AccessBaseVO {

    @Serial
    private static final long serialVersionUID = 3216959020642419661L;

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 显示服务名¬
     */
    @NotBlank(message = "接入编码不能为空")
    @Size(min = 1, max = 50, message = "接入编码长度必须在1-50之间")
    private String code;
}
