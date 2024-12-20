package cn.wnhyang.coolGuard.vo.base;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class ApplicationBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1890893104399246660L;

    /**
     * 显示名
     */
    @NotBlank(message = "显示名不能为空")
    private String displayName;

    /**
     * 描述
     */
    private String description;
}
