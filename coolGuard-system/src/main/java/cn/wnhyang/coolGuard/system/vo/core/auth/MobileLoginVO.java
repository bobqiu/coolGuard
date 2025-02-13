package cn.wnhyang.coolGuard.system.vo.core.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2023/8/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileLoginVO {

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String mobile;
}
