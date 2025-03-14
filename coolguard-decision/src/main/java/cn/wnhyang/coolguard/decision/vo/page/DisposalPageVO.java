package cn.wnhyang.coolguard.decision.vo.page;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 处置编码
     */
    private String code;

    /**
     * 处置名
     */
    private String name;

}
