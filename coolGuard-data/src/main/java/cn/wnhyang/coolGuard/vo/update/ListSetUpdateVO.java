package cn.wnhyang.coolGuard.vo.update;

import lombok.Data;

import java.io.Serial;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetUpdateVO extends ListDataUpdateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
