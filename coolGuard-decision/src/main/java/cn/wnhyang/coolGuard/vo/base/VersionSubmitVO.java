package cn.wnhyang.coolGuard.vo.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/11/21
 **/
@Data
public class VersionSubmitVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -603827310071440471L;

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "版本描述不能为空")
    private String versionDesc;
}
