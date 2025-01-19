package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Data
public class FieldRefPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 被引用类型
     */
    private String refType;

    /**
     * 被引用
     */
    private String refBy;

    /**
     * 被引用子类型
     */
    private String refSubType;
}
