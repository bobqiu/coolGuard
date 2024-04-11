package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务配置字段表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ServiceConfigFieldDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 服务id
     */
    private Long serviceId;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 是否必须
     */
    private Boolean required;

    /**
     * 字段id
     */
    private String fieldName;
}
