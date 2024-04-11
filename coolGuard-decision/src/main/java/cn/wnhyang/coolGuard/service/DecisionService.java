package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.context.DecisionResponse;

import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
public interface DecisionService {

    /**
     * 同步调用
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    DecisionResponse syncRisk(String name, Map<String, String> params);

    /**
     * 异步调用
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    DecisionResponse asyncRisk(String name, Map<String, String> params);
}
