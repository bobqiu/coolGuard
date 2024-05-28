package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
