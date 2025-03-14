package cn.wnhyang.coolguard.decision.vo.create;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListDataCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名单集id
     */
    private String listSetCode;

    /**
     * 名单数据
     */
    private String value;

    /**
     * 名单数据来源
     */
    private String source;

    /**
     * 描述
     */
    private String description;
}
