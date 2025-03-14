package cn.wnhyang.coolguard.decision.vo.page;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class RuleVersionPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 编码
     */
    private String code;
}
