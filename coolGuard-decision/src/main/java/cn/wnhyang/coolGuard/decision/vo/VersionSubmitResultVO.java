package cn.wnhyang.coolGuard.decision.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/11/22
 **/
@Data
public class VersionSubmitResultVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6177443339834044186L;

    private Long id;

    private Boolean success;

    private String msg;
}
