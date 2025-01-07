package cn.wnhyang.coolGuard.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
public class DictDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    private Long id;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典数据
     */
    private String data;

    /**
     * 标准
     */
    private Boolean standard;

    /**
     * 描述
     */
    private String description;
}
