package cn.wnhyang.coolguard.decision.vo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 954520164753579904L;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则编码
     */
    private String code;

    public RuleResult(String name, String code, String express) {
        this.name = name;
        this.code = code;
        this.express = express;
    }

    /**
     * 处置名称
     */
    private String disposalName;

    /**
     * 处置编码
     */
    private String disposalCode;

    /**
     *
     */
    private String express;

}
