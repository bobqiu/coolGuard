package cn.wnhyang.coolGuard.decision.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author wnhyang
 * @date 2024/7/18
 **/
public class LFUtil {

    /**
     * policySetCode
     */
    public static final String POLICY_SET_CHAIN = "PS_C#{}";

    /**
     * 策略普通组件
     */
    public static final String POLICY_NODE = "policy";

    /**
     * @param nodeId 组件id
     * @param tag    标签
     * @return 带标签的组件
     */
    public static String getNodeWithTag(String nodeId, String tag) {
        return StrUtil.format("{}.tag(\"{}\")", nodeId, tag);
    }

    public static void main(String[] args) {

    }
}
