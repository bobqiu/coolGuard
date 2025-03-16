package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.decision.vo.result.DecisionResult;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2025/3/8
 **/
public interface DecisionService {

    /**
     * 测试
     *
     * @param code   服务code
     * @param params 参数
     * @return map
     */
    DecisionResult testRisk(String code, Map<String, String> params);

    /**
     * 同步调用
     *
     * @param code   服务code
     * @param params 参数
     * @return map
     */
    DecisionResult syncRisk(String code, Map<String, String> params);

    /**
     * 异步调用
     *
     * @param code   服务code
     * @param params 参数
     * @return map
     */
    DecisionResult asyncRisk(String code, Map<String, String> params);
}
