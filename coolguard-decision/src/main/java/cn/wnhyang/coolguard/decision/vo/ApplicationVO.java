package cn.wnhyang.coolguard.decision.vo;

import cn.wnhyang.coolguard.decision.vo.base.ApplicationBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class ApplicationVO extends ApplicationBaseVO {

    @Serial
    private static final long serialVersionUID = -358430182210185582L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用编码
     */
    private String code;

    /**
     * 密钥
     */
    private String secret;
}
