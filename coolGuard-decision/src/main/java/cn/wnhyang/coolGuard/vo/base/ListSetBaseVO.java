package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/12/8
 **/
@Data
public class ListSetBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4247055449256912424L;

    /**
     * 名单集名
     */
    private String name;

    /**
     * 名单集类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;
}
