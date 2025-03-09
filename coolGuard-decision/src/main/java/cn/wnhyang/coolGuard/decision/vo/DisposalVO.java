package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.vo.base.DisposalBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalVO extends DisposalBaseVO {

    @Serial
    private static final long serialVersionUID = -4922317764227473253L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 处置编码
     */
    private String code;

    /**
     * 是否为标准
     */
    private Boolean standard;
}
