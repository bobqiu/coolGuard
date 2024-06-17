package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class PolicyBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7548106664553347287L;

    /**
     * chain名
     */
    private String chainName;

    /**
     * 策略名
     */
    private String name;

    /**
     * 策略状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}
