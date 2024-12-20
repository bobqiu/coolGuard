package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.TagBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 标签表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
public class TagUpdateVO extends TagBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
