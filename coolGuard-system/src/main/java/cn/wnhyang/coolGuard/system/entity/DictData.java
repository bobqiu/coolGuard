package cn.wnhyang.coolGuard.system.entity;

import cn.wnhyang.coolGuard.entity.LabelValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2025/1/3
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictData extends LabelValue implements Serializable {

    @Serial
    private static final long serialVersionUID = -5001668101437399004L;

    /**
     * 颜色
     */
    private String color;

}
