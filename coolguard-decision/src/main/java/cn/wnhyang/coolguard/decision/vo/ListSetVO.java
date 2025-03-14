package cn.wnhyang.coolguard.decision.vo;

import cn.wnhyang.coolguard.decision.vo.base.ListSetBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetVO extends ListSetBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名单集编码
     */
    private String code;
}
