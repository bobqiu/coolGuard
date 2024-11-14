package cn.wnhyang.coolGuard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/6/19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -1117826649441473613L;

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 指标名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 值
     */
    private Object value;
}
