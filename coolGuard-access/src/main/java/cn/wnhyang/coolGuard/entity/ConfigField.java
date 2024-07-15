package cn.wnhyang.coolGuard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/5/9
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigField implements Serializable {

    @Serial
    private static final long serialVersionUID = 7404603500773898388L;

    /**
     * 参数名
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 是否必须
     */
    @TableField("required")
    private Boolean required;

    /**
     * 字段id
     */
    @TableField("field_name")
    private String fieldName;
}
