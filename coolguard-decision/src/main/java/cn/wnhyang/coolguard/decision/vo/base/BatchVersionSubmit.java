package cn.wnhyang.coolguard.decision.vo.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author wnhyang
 * @date 2024/11/22
 **/
@Data
public class BatchVersionSubmit implements Serializable {

    @Serial
    private static final long serialVersionUID = 8837081015018704771L;

    @NotNull(message = "ids不能为空")
    private Set<Long> ids;

    @NotBlank(message = "版本描述不能为空")
    private String versionDesc;
}
