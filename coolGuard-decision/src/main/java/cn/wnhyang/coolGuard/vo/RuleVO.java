package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.RuleBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 规则表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class RuleVO extends RuleBaseVO {

    @Serial
    private static final long serialVersionUID = 2080884044993438928L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 策略编码
     */
    private String policyCode;

    /**
     * 规则编码
     */
    private String code;

    /**
     * 策略状态
     */
    private Boolean publish;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 版本描述
     */
    private String versionDesc;
}
