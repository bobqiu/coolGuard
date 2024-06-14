package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名单集id
     */
    private Long listSetId;

    /**
     * 名单数据
     */
    private String value;

    /**
     * 名单数据来源
     */
    private String source;

    /**
     * 名单集状态
     */
    private Boolean status;

    /**
     * 描述
     */
    private String description;
}
