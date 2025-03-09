package cn.wnhyang.coolGuard.decision.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2025/2/27
 **/
@Data
public class IndicatorSimpleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 指标版本编码
     */
    private String code;

    /**
     * 指标名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 版本号
     */
    private Integer version;

}
