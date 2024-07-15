package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class DisposalBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2853793969240349334L;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 颜色
     */
    private String color;

    /**
     * 描述
     */
    private String description;

}
