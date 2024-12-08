package cn.wnhyang.coolGuard.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 策略集表版本
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Data
public class PolicySetVersionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略集状态
     */
    private Boolean latest;

    /**
     * 策略集链路
     */
    private String chain;

    /**
     * 版本号
     */
    private Integer version;
}
