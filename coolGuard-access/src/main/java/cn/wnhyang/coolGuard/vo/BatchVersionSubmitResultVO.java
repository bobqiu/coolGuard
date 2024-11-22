package cn.wnhyang.coolGuard.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/11/22
 **/
@Data
public class BatchVersionSubmitResultVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6177443339834044186L;

    private Long id;

    private Boolean success;

    private String msg;
}
