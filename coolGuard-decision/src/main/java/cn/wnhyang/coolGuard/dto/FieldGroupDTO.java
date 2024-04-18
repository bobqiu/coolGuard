package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/16
 **/
@Data
public class FieldGroupDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8478787293798089192L;

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 显示分组名
     */
    private String displayName;

    /**
     * 分组标识
     */
    private String name;

    /**
     * 是否为标准
     */
    private Boolean standard;

    /**
     * 描述
     */
    private String description;

    /**
     * 组内字段数
     */
    private Long count;

}
