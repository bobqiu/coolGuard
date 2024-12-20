package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.ListDataCreateVO;
import lombok.Data;

import java.io.Serial;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListDataUpdateVO extends ListDataCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
