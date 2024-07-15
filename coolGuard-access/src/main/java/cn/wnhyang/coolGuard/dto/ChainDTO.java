package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ChainDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
     * 路由
     */
    private String route;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 描述
     */
    private String description;
}
