package cn.wnhyang.coolGuard.decision.vo.create;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * chain名
     */
    private String chainName;

    /**
     * el数据
     */
    private String elData;

    /**
     * chain状态
     */
    private Boolean enable;

    /**
     * 描述
     */
    private String description;
}
