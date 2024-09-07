package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicySetVersionPageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
