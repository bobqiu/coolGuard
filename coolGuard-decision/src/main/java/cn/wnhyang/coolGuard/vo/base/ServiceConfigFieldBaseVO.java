package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class ServiceConfigFieldBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7003521356474368777L;

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
