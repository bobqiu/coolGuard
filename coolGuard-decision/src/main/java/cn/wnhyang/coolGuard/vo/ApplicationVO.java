package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.ApplicationBaseVO;
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
     * 应用名
     */
    private String name;

    /**
     * 密钥
     */
    private String secret;
}
