package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class ApplicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 应用名
     */
    private String name;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 描述
     */
    private String description;
}
