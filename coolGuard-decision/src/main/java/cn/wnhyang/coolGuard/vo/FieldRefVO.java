package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import lombok.Data;

import java.io.Serial;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Data
public class FieldRefVO extends FieldRefCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
