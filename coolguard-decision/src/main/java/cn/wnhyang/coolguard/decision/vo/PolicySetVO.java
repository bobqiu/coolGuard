package cn.wnhyang.coolguard.decision.vo;

import cn.wnhyang.coolguard.decision.vo.base.PolicySetBaseVO;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class PolicySetVO extends PolicySetBaseVO {

    @Serial
    private static final long serialVersionUID = -3067674637266164651L;

    /**
     * 主键
     */
    private Long id;

    /**
     * app名
     */
    private String appCode;

    /**
     * 策略集编码
     */
    private String code;

    /**
     * 策略集链
     */
    private String chain;

    /**
     * 发布
     */
    private Boolean publish;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 策略
     */
    private List<PolicyVO> policyList;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 版本描述
     */
    private String versionDesc;
}
