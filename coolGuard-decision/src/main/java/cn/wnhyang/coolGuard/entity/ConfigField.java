package cn.wnhyang.coolGuard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2024/5/9
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigField {

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
