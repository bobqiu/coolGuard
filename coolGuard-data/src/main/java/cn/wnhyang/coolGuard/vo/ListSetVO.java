package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.ListSetCreateVO;
import lombok.Data;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetVO extends ListSetCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
