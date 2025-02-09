package cn.wnhyang.coolGuard.vo.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class AccessBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5565734558672093346L;

    /**
     * 接入名
     */
    @NotBlank(message = "接入名不能为空")
    @Size(min = 1, max = 50, message = "接入名长度必须在1-50之间")
    private String name;

    /**
     * 描述
     */
    private String description;
}
