package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.ServiceConfigBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class ServiceConfigCreateVO extends ServiceConfigBaseVO {

    @Serial
    private static final long serialVersionUID = -5143580505431009435L;

    /**
     * 服务标识
     */
    @NotBlank(message = "字段名不能为空")
    @Size(min = 1, max = 30, message = "字段名长度必须在1-30之间")
    private String name;

}
