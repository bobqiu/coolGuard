package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListDataPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名单数据
     */
    private String value;
}
