package cn.wnhyang.coolGuard.system.vo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamCreateVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 参数标签
     */
    private String label;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 参数数据
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
