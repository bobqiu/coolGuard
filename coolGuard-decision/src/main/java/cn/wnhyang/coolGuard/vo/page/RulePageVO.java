package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RulePageVO extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 规则名
     */
    private String name;

    /**
     * 处理编码
     */
    private Long disposalId;
}
