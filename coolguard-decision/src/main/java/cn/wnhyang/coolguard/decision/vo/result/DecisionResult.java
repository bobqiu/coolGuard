package cn.wnhyang.coolguard.decision.vo.result;

import lombok.Data;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2025/3/16
 **/
@Data
public class DecisionResult {

    private PolicySetResult policySetResult;

    private Map<String, Object> outFields;
}
