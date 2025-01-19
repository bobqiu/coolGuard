package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.ApplicationBaseVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class ApplicationCreateVO extends ApplicationBaseVO {

    @Serial
    private static final long serialVersionUID = -4178585550757568445L;

    /**
     * 应用名
     */
    @NotBlank(message = "应用名不能为空")
    @Size(min = 1, max = 30, message = "应用名长度必须在1-30之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "应用名只能包含字母和数字")
    private String code;

    /**
     * 密钥
     */
    private String secret;
}
