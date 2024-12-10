package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/12/8
 **/
@Data
public class TagBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2950711324993695888L;

    /**
     * 标签名
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

    /**
     * 描述
     */
    private String description;
}
