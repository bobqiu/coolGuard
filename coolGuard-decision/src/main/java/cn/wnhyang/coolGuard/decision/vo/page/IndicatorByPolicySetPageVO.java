package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/5/8
 **/
@Data
public class IndicatorByPolicySetPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -3210964069305813159L;

    @NotNull(message = "策略集编码不能为空")
    private String policySetCode;
}
